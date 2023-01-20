package com.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.*;
import com.schedule.mapper.FlowMapper;
import com.schedule.service.*;
import com.schedule.util.CalculateUtil;
import com.schedule.util.CalculateUtilTest;
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



    @Override
    public void calculate(Long storeId, String startDate, String endDate) {

        /**
         * 为算法做准备 将需要用到的数据全部查询出来
         */
        //根据日期获取flow的记录
        LambdaQueryWrapper<Flow> flowWrapper = new LambdaQueryWrapper<>();
        flowWrapper.eq(Flow::getStoreId,storeId)
                .ge(Flow::getDate,startDate)
                .le(Flow::getDate,endDate);
        List<Flow> flowList = this.list(flowWrapper);

        //根据商店id查询员工，再根据员工id查询偏好
        LambdaQueryWrapper<Staff> staffWrapper = new LambdaQueryWrapper<>();
        staffWrapper.eq(Staff::getStoreId,storeId);
        List<Staff> staffList = staffService.list(staffWrapper);
        List<Long> staffIds = new ArrayList<>();
        for (Staff staff : staffList) {
            staffIds.add(staff.getId());
        }
        LambdaQueryWrapper<Preference> preferenceWrapper = new LambdaQueryWrapper<>();
        preferenceWrapper.in(Preference::getStaffId,staffIds);
        List<Preference> preferenceList = preferenceService.list(preferenceWrapper);

        //把偏好和员工进行捆绑
        HashMap<Long, StaffWithPre> staffWithPreMap = new HashMap<>();
        for (Staff staff : staffList) {
            StaffWithPre staffWithPre = new StaffWithPre();
            BeanUtils.copyProperties(staff,staffWithPre);
            staffWithPreMap.put(staff.getId(), staffWithPre);
        }
        for (Preference preference : preferenceList) {
            Long staffId = preference.getStaffId();
            StaffWithPre staffWithPre = staffWithPreMap.get(staffId);
            String type = preference.getPreferenceType();
            switch (type){
                case "工作日偏好" : staffWithPre.setDayPre(preference.getValue()); break;
                case "工作时间偏好" : staffWithPre.setWorkTimePre(preference.getValue()); break;
                default : staffWithPre.setShiftTimePre(preference.getValue());
            }
            staffWithPreMap.put(staffId,staffWithPre);
        }

        //根据商店id获取商店信息
        Store store = storeService.getById(storeId);

        //根据商店id获取商店规则
        LambdaQueryWrapper<Rule> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(Rule::getStoreId,storeId);
        List<Rule> ruleList = ruleService.list(ruleWrapper);

        //偏好数据提取


        /**
         * 算法设计 根据flowList客流量表生成排班表（未填入员工）
         */

        double n1,n2,n4,j2,k1,k2,k3,closeHour;

        //开店规则
        //n1,k1  表示开店 n1 个小时前需要有员工当值，当值员工数为门店面积除以 k1
        double size = store.getSize();
        n1 = 1;
        k1 = 100;



        //关店规则
        //"n2,j2,k2”表示关店 n2 个小时内需要有员工当值，当值员工数不小于 j2 并且不小于门店面积除以 k2
        n2 = 2;
        j2 = 3;
        k2 = 13;


        //客流规则
        //k3 表示按照业务预测数据，每 k3 个客流必须安排至少一个员工当值
        k3 = 3.8;

        //值班规则
        //"n4"表示如果没有客流量的时候，至少需要 n4 个店员值班.
        n4 = 1;

        //职位规则
        //"open" : "收银,经理" , "duty" : "经理,导购" , "close" : "收银"
        String ffarr [][]= null;


        for(Rule rule:ruleList){
            String ruleType=rule.getRuleType();
            switch (ruleType){
                case "开店规则" : double[] farr =rule.getArr(); n1=farr[0]; k1=farr[1]; break;
                case "关店规则" : double[] sarr =rule.getArr(); n2=sarr[0]; j2=sarr[1];k2=sarr[2]; break;
                case "客流规则" : double[] tarr =rule.getArr(); k3=tarr[0];  break;
                case "值班规则" : double[] foarr =rule.getArr(); n4=foarr[0]; break;
                case "职位规则" : String [] fiarr=rule.getValue().split("|");
                                 ffarr= new String[3][];
                                 ffarr[0]=fiarr[0].split(",");
                                 ffarr[1]=fiarr[1].split(",");
                                 ffarr[2]=fiarr[2].split(",");
            }
        }


        //开店规则员工值
        double openNum = size / k1;

        //关店规则员工值
        double closeNum = Math.max( j2 , size / k2);


        List<Stack<Plan>> plan = CalculateUtil.getPlan(flowList, f(n1), f(openNum), f(n2), f(closeNum), k3, f(n4), ffarr);

        for (Stack<Plan> stack : plan) {
            stack.forEach(System.out::println);
        }

    }



}
