package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.BaseContext;
import com.schedule.common.R;
import com.schedule.entity.*;
import com.schedule.service.FlowService;
import com.schedule.service.GroupService;
import com.schedule.service.PlanService;
import com.schedule.service.StaffGroupService;
import com.schedule.util.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * plan
 * @author akuya
 * @create 2023-02-01-18:48
 */

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private FlowService flowService;

    public static int calculateHourDifference(String input) {
        //分割字符串，得到开始时间和结束时间
        String[] times = input.split("--");
        String startTime = times[0];
        String endTime = times[1];

        //将时间转换为小时数
        int startHours = convertToHours(startTime);
        int endHours = convertToHours(endTime);

        //计算小时差
        int hourDifference = endHours - startHours;

        //返回小时差
        return hourDifference;
    }

    public static int convertToHours(String time) {
        //分割时间，得到小时
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);

        //返回小时数
        return hour;
    }

    public static int getStartHour(String input) {
        //分割字符串，得到开始时间
        String[] times = input.split("-");
        String startTime = times[0];

        //将时间转换为小时数
        int startHour = convertToHours(startTime);

        //返回开始时间的小时数
        return startHour;
    }
    /**
     * 添加数据
     * @param planWithStaff
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody PlanWithStaff planWithStaff){


        System.out.println(planWithStaff.toString());
        int workTime = calculateHourDifference(planWithStaff.getTime());
        planWithStaff.setWorkTime(workTime);
        planWithStaff.setStartTime(getStartHour(planWithStaff.getTime()));

        planService.save(planWithStaff);

        System.out.println("添加成功!@!!!!!!!!!!!!!");
        return R.msg("添加成功");

    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        planService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 修改数据
     * @param planWithStaff
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody PlanWithStaff planWithStaff){


        planService.updateById(planWithStaff);
        return R.msg("修改成功");
    }

    /**
     * 条件查询
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping
    public R<List<PlanWithStaff>> getAll(@RequestParam Long storeId, String startDate, String endDate){
        LambdaQueryWrapper<PlanWithStaff> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PlanWithStaff::getStoreId, storeId)
                .ge(startDate != null,PlanWithStaff::getDate, startDate)
                .le(endDate != null,PlanWithStaff::getDate, endDate);
        List<PlanWithStaff> list = planService.list(wrapper);
        for(PlanWithStaff p:list){
            int start=p.getStartTime();
            int end=start+p.getWorkTime();
            if((start<=11&&end>=14)||(start<=17&&end>=20)){
                p.setFlag(true);
            }
        }

        List<Information> informationList = planService.getInformation(storeId);

        for (Information information : informationList) {
            System.out.println(information);
        }
        for (int i = 0; i < list.size(); i ++) {

            for (Information information : informationList) {
                PlanWithStaff planStaff = list.get(i);
                if(planStaff.getStaffId() != null && planStaff.getStaffId().equals(information.getStaId())){
                    PlanWithStaff planWithStaff = list.get(i);
                    planWithStaff.setRole(information.getRole());
                    planWithStaff.setGroupName(information.getGroupName());
                }

            }

        }


        return R.success(list);
    }


    @Autowired
    GroupService groupService;

    @Autowired
    StaffGroupService staffGroupService;

    /**
     * 生成排班表
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/calculate/{storeId}")
    public R<List<PlanWithStaff>> getPlan(@PathVariable Long storeId, String startDate, String endDate){
        long t1 = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LambdaQueryWrapper<PlanWithStaff> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(PlanWithStaff::getStoreId, storeId)
                        .ge(PlanWithStaff::getDate, startDate)
                        .le(PlanWithStaff::getDate, endDate);
                planService.remove(wrapper);
            }
        });

        thread.start();
        long t5 = System.currentTimeMillis();
        List<Plan> planList= flowService.calculate(storeId,startDate,endDate);
        long t6 = System.currentTimeMillis();

        List<PlanWithStaff> planWithStaffList=new ArrayList<>();


        for(Plan plan:planList) {
            PlanWithStaff planWithStaff =new PlanWithStaff();
            if (plan.getStaff() != null) {
                planWithStaff.setStaff(plan.getStaff().getName());
            }
            int endTime = plan.getStartTime() + plan.getWorkTime();
            String time;
            time = plan.getStartTime() + ":00" + "--" + endTime + ":00";
            planWithStaff.setTime(time);
            planWithStaff.setStaffId(plan.getStaffId());
            planWithStaff.setWork(plan.getWorkType());
            planWithStaff.setDate(plan.getDate());
            planWithStaff.setStoreId(storeId);
            planWithStaff.setStartTime(plan.getStartTime());
            planWithStaff.setWorkTime(plan.getWorkTime());
            planWithStaffList.add(planWithStaff);
        }

//        System.out.println("处理数据所耗时间：" + (t6 - t5) + "ms");

        try{
            thread.join();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        long t3 = System.currentTimeMillis();
        planService.saveBatch(planWithStaffList);
        long t2 = System.currentTimeMillis();

        System.out.println("存数据所耗时间：" + (t2 - t3) + "ms");
        System.out.println("总所耗时间：" + (t2 - t1) + "ms");




        //添加小组类型
//        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
//        Long userId = BaseContext.getUserId();
//        wrapper.eq(Group::getUserId, userId);
//        List<Group> groups = groupService.list(wrapper);

//        List<Information> informationList = planService.getInformation(storeId);
//        for (int i = 0; i < planWithStaffList.size(); i ++) {
//
//            for (Information information : informationList) {
//
//                if(planWithStaffList.get(i).getStaffId().equals(information.getStaId())){
//                    PlanWithStaff planWithStaff = planWithStaffList.get(i);
//                    planWithStaff.setRole(information.getRole());
//                    planWithStaff.setGroupName(information.getGroupName());
//                }
//
//            }
//
//        }



        return R.success(planWithStaffList);
   }


}
