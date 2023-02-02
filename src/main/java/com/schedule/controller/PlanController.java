package com.schedule.controller;

import com.schedule.common.R;
import com.schedule.entity.Plan;
import com.schedule.entity.PlanWithStaff;
import com.schedule.entity.Staff;
import com.schedule.service.FlowService;
import com.schedule.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
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

    @PostMapping("/add")
    public R<String> add(@RequestBody PlanWithStaff planWithStaff){
        planService.save(planWithStaff);
        return R.msg("添加成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        planService.removeByIds(ids);
        return R.msg("删除成功");
    }

    @GetMapping("/calculate/{storeId}")
    public R<List<PlanWithStaff>> getPlan(@PathVariable Long storeId, String startDate, String endDate){
        List<Plan> planList= flowService.calculate(storeId,startDate,endDate);
        List<PlanWithStaff> planWithStaffList=new ArrayList<>();
        PlanWithStaff planWithStaff =new PlanWithStaff();

        for(Plan plan:planList) {
            if (plan.getStaff() != null) {
                planWithStaff.setStaff(plan.getStaff().getName());
            }
            int endtime = plan.getStartTime() + plan.getWorkTime();
            String time;
            time = plan.getStartTime() + ":00" + "--" + endtime + ":00";
            planWithStaff.setTime(time);
            planWithStaff.setStaffId(plan.getStaffId());
            planWithStaff.setWork(plan.getWorkType());
            planWithStaff.setDate(plan.getDate());
            planWithStaff.setStoreId(storeId);
            planWithStaffList.add(planWithStaff);

        }

        planService.saveBatch(planWithStaffList);

        return R.success(planWithStaffList);
   }


}
