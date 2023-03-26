package com.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.common.CustomException;
import com.schedule.entity.*;
import com.schedule.mapper.FlowMapper;
import com.schedule.service.*;
import com.schedule.util.CalculateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.schedule.util.CalculateUtil.f;

@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {

    @Autowired
    RuleService ruleService;

    @Autowired
    StaffService staffService;

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    StoreService storeService;

    @Autowired
    PlanService planService;

    @Autowired
    FlowService flowService;


    HashMap<Long, StaffWithPre> map = new HashMap<>();
    Store store;
    List<Rule> ruleList;
    List<Flow> flowList;
    @Override
    public List<Plan> calculate(Long storeId, String startDate, String endDate) {

        /**
         * 为算法做准备 将需要用到的数据全部查询出来
         */
        //根据日期获取flow的记录
        int days;

//        Thread thread4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
                LambdaQueryWrapper<Flow> flowWrapper = new LambdaQueryWrapper<>();
                flowWrapper.eq(Flow::getStoreId,storeId)
                        .ge(Flow::getDate,startDate)
                        .le(Flow::getDate,endDate);
                flowList = flowService.list(flowWrapper);
                days = CalculateUtil.dateDifference(startDate, endDate);
                if(flowList.size() != days + 1)throw new CustomException(startDate + "至" + endDate + "的数据不完整，请先添加顾客预测流量数据" );

//            }
//        });

        //根据商店id查询员工，再根据员工id查询偏好

//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
                LambdaQueryWrapper<Staff> staffWrapper = new LambdaQueryWrapper<>();
                staffWrapper.eq(Staff::getStoreId,storeId);
                List<Staff> staffList = staffService.list(staffWrapper);
                List<Long> staffIds = new ArrayList<>();
                for (Staff staff : staffList) {
                    staffIds.add(staff.getId());
                }
                LambdaQueryWrapper<Preference> preferenceWrapper = new LambdaQueryWrapper<>();
                if(staffIds.size() == 0)throw new CustomException("此商店的员工数量为0,无法生成排班表");
                preferenceWrapper.in(Preference::getStaffId,staffIds);
                List<Preference> preferenceList = preferenceService.list(preferenceWrapper);

                //把偏好和员工进行捆绑

                for (Staff staff : staffList) {
                    StaffWithPre staffWithPre = new StaffWithPre();
                    BeanUtils.copyProperties(staff,staffWithPre);
                    map.put(staff.getId(), staffWithPre);
                }
                int preNum;
                for (Preference preference : preferenceList) {
                    preNum=0;
                    Long staffId = preference.getStaffId();
                    StaffWithPre staffWithPre = map.get(staffId);
                    String type = preference.getPreferenceType();
                    switch (type){
                        case "工作日偏好" : staffWithPre.setDayPre(preference.getValue());preNum++; break;
                        case "工作时间偏好" : staffWithPre.setWorkTimePre(preference.getValue()); preNum++;break;
                        default : staffWithPre.setShiftTimePre(preference.getValue());preNum++;
                    }
                    staffWithPre.setPreNum(preNum);
                    map.put(staffId,staffWithPre);
                }
//            }
//        });


        //根据商店id获取商店信息

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                store = storeService.getById(storeId);
            }
        });


        //根据商店id获取商店规则
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                LambdaQueryWrapper<Rule> ruleWrapper = new LambdaQueryWrapper<>();
                ruleWrapper.eq(Rule::getStoreId,storeId);
                ruleList = ruleService.list(ruleWrapper);

            }
        });

        long t1 = System.currentTimeMillis();

//        thread1.start();
        thread2.start();
        thread3.start();
        try{
            thread2.join();
            thread3.join();
//            thread1.join();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        long t2 = System.currentTimeMillis();
        System.out.println("查表所耗时间：" + (t2 - t1) + "ms");

        //偏好数据提取


        /**
         * 算法设计 根据flowList客流量表生成排班表（未填入员工）
         */
        long t3 = System.currentTimeMillis();
        double n1,n2,n4,j2,k1,k2,k3,closeHour;

        //开店规则
        //n1,k1  表示开店 n1 个小时前需要有员工当值，当值员工数为门店面积除以 k1
        double size = store.getSize();
        n1 = 1;
        k1 = 100;



        //关店规则
        //"n2,j2,k2”表示关店 n2 个小时内需要有员工当值，人数 = 门店面积除以 k2 + j2
        n2 = 2;
        j2 = 1;
        k2 = 80;


        //客流规则
        //k3 表示按照业务预测数据，每 k3 个客流必须安排至少一个员工当值
        k3 = 3.8;

        //值班规则
        //"n4"表示如果没有客流量的时候，至少需要 n4 个店员值班.
        n4 = 1;

        //职位规则
        //"open" : "收银,经理" , "duty" : "经理,导购" , "close" : "收银"
        String ffarr [][]= null;

        //休息规则
        //每连续工作4小时，休息k小时
        int relax = 1;

        for(Rule rule:ruleList){
            String ruleType=rule.getRuleType();
            switch (ruleType){
                case "开店规则" : double[] farr =rule.fGetArr(); n1=farr[0]; k1=farr[1]; break;
                case "关店规则" : double[] sarr =rule.fGetArr(); n2=sarr[0]; j2=sarr[1];k2=sarr[2]; break;
                case "客流规则" : double[] tarr =rule.fGetArr(); k3=tarr[0];  break;
                case "值班规则" : double[] foarr =rule.fGetArr(); n4=foarr[0]; break;
                case "职位规则" : String [] fiarr=rule.getValue().split("|");
                                 ffarr= new String[3][];
                                 ffarr[0]=fiarr[0].split(",");
                                 ffarr[1]=fiarr[1].split(",");
                                 ffarr[2]=fiarr[2].split(",");
                                 break;
                case "休息规则" : relax = Integer.parseInt(rule.getValue());
            }
        }




        //开店规则员工值
        double openNum = size / k1;

        //关店规则员工值
        double closeNum = size / k2 + j2;


        List<List<Plan>> plans = CalculateUtil.getPlan(flowList, f(n1), f(openNum), f(n2), f(closeNum), k3, f(n4), ffarr);

        /**
         * 计算每个班次符合条件的人数
         */
        List<Plan> sortedPlan = new LinkedList<>();

        int day = 0;
        for (List<Plan> plan : plans) {
            int shift = 0;
            for (Plan unit : plan) {
                List<StaffWithPre> list = new ArrayList<>();
                for (Map.Entry<Long, StaffWithPre> entry : map.entrySet()) {
//                    System.out.println(entry.getValue());
                    StaffWithPre staff = entry.getValue();
                    unit.setDay(day);
                    unit.setShift(shift);
                    //判断工作日偏好
                    Boolean weekDayFlag = false;
                    if(staff.getDayPre() != null){
                        int[] arrD = staff.getArrD();
                        for (int i : arrD) {
                            if(i == unit.getWeekDay())weekDayFlag = true;
                        }
                    }else weekDayFlag = true;

                    //判断工作时间偏好
                    Boolean workTimeFlag = false;
                    if(staff.getWorkTimePre() != null){
                        int[][] arrW = staff.getArrW();
                        for (int[] arr : arrW) {
                            int startTime = unit.getStartTime();
                            int endTime = startTime + unit.getWorkTime();

                            if(arr[0] <= startTime && arr[1] >= endTime){
                                workTimeFlag = true;
                                break;
                            }
                        }
                    }else workTimeFlag = true;

                    //判断工作类型
                    Boolean workTypeFlag = false;
                    String[] job = unit.getJob();
                    for (String s : job) {
                        if(s.equals(staff.getRole())) workTypeFlag = true;
                    }

                    if(weekDayFlag && workTimeFlag && workTypeFlag){
                        list.add(staff);
                        int num = staff.getNum();
                        staff.setNum(num + 1);
                        map.put(staff.getId(), staff);
                    }

                }

                unit.setNum(list.size());
                unit.setList(list);
                shift ++;
                sortedPlan.add(unit);
//                System.out.println(unit);
//                System.out.println();
            }
            day ++;
        }

        //计算时间重复次数
//        List<Plan> sortedPlan2 = new LinkedList<>(sortedPlan);
//        int[][] stat = new int[day][24];
//
//        for (Plan plan : sortedPlan2) {
//            Integer day1 = plan.getDay();
//            Integer startTime = plan.getStartTime();
//            stat[day1][startTime] ++;
//        }
//        for (Plan plan : sortedPlan2) {
//            Integer day1 = plan.getDay();
//            Integer startTime = plan.getStartTime();
//            plan.setRepeat(stat[day1][startTime]);
//        }


        Collections.sort(sortedPlan, new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return o1.getNum() - o2.getNum();
            }
        });

//        Collections.sort(sortedPlan2, new Comparator<Plan>() {
//            @Override
//            public int compare(Plan o1, Plan o2) {
//                return  o2.getRepeat() - o1.getRepeat();
//            }
//        });

//        for (Plan s : sortedPlan) {
//            System.out.println(s);
//        }


//        System.out.println(sortedPlan);

        //初始化sign数组 设置工作时长
        for (Map.Entry<Long, StaffWithPre> entry : map.entrySet()) {
            StaffWithPre staff = entry.getValue();
            staff.setSign(new boolean[day][24]);

            int[] arr = staff.getArrS();
            int[] dayWorkTime = new int[day];
            int[] weekWorkTime = new int[day / 7 + 1];
            Arrays.fill(dayWorkTime, arr[0]);
            Arrays.fill(weekWorkTime, arr[1]);
            staff.setDayWorkTime(dayWorkTime);
            staff.setWeekWorkTime(weekWorkTime);

            map.put(entry.getKey(), staff);
//            System.out.println(entry.getValue());
        }

        int total = 0;
        for (Plan plan : sortedPlan) {
            List<StaffWithPre> list = plan.getList();
            Integer today = plan.getDay();

            Queue<StaffWithPre> queue = new LinkedList<>();
            for (StaffWithPre staff : list) {

                //把名单插入
                Long id = staff.getId();
                StaffWithPre staffWithPre = map.get(id);
                queue.add(staffWithPre);
                //判断他们的班次是否冲突 是否有间隔
            }

//            for (StaffWithPre staffWithPre : queue) {
//                System.out.println("**********");
//                System.out.println(staffWithPre + " " + staffWithPre.getNum());
//            }


            while (!queue.isEmpty()){
                StaffWithPre staff = queue.poll();
                boolean[][] copy = Arrays.stream(staff.getSign())
                        .map(boolean[]::clone)
                        .toArray(boolean[][]::new);
                int[] dayWorkTime = staff.getDayWorkTime();
                int[] weekWorkTime = staff.getWeekWorkTime();
                Integer workTime = plan.getWorkTime();
                boolean tag = false;
                if(dayWorkTime[today] >= workTime && weekWorkTime[today / 7] >= workTime){
                    boolean[][] sign = staff.getSign();
                    boolean flag = true;
                    //判断时间是否冲突
                    for (Integer i = 0; i < plan.getWorkTime(); i++) {
                        Integer startTime = plan.getStartTime();
                        if(sign[today][startTime + i] == true) flag = false;
                    }

                    //标记对应的时间已被占用
                    if(flag == true){
                        //标记
                        for (Integer i = 0; i < plan.getWorkTime(); i++) {
                            Integer startTime = plan.getStartTime();
                            sign[today][startTime + i] = true;
                        }
                        //判断是否有连续工作超过4h
                        int tmp = 0;
                        for (int i = 0; i < sign[today].length; i++) {
                            if (sign[today][i]){
                                tmp ++;
                                if(tmp >= 4){
                                    //每工作4h要休息relax小时
                                    for (int j = 1; j <= relax && i + j < 24; j++) {
                                        if(sign[today][i + j])flag = false;
                                    }
                                    if (tmp > 4)flag = false;
                                }
                            }else tmp = 0;
                        }

                        if (flag){
                            staff.setSign(sign);
                            dayWorkTime[today] -= workTime;
                            weekWorkTime[today / 7] -= workTime;
                            staff.setDayWorkTime(dayWorkTime);
                            staff.setWeekWorkTime(weekWorkTime);
                            map.put(staff.getId(), staff);
                            plan.setStaff(staff);
                            plan.setStaffId(staff.getId());
                            tag = true;
                            total ++;
                        }else{
//                            sign = Arrays.stream(copy)
//                                    .map(boolean[]::clone)
//                                    .toArray(boolean[][]::new);
                            staff.setSign(copy);
                        }
                    }
                }
                if(tag)break;
            }
        }

        Collections.sort(sortedPlan, new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                o1.getDay();
                o2.getDay();
                o1.getShift();

                if(o1.getDay() == o2.getDay())return o1.getShift() - o2.getShift();
                return o1.getDay() - o2.getDay();
            }
        });

//        for (Plan plan : sortedPlan) {
//            System.out.println(plan);
//            System.out.println();
//        }

        System.out.print("已成功排班比例：");
        System.out.println(total * 1.0 / sortedPlan.size());

//        for (Map.Entry<Long, StaffWithPre> entry : map.entrySet()) {
//
//            System.out.print("员工姓名：");
//            System.out.println(entry.getValue().getName());
//            System.out.print("每天剩余时间：");
//            for (int i : entry.getValue().getDayWorkTime()) {
//                System.out.print(i + ",");
//            }
//            System.out.println();
//            System.out.print("每周剩余时间：");
//            for (int i : entry.getValue().getWeekWorkTime()) {
//                System.out.print(i + ",");
//            }
//            System.out.println();
//            System.out.print("对应班次数量：");
//            System.out.println(entry.getValue().getNum());
//            System.out.println();
//        }
        long t4 = System.currentTimeMillis();
        System.out.println("计算所耗时间：" + (t4 - t3) + "ms");
        return sortedPlan;

    }





}
