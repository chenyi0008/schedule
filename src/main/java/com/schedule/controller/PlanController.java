package com.schedule.controller;

import com.schedule.common.R;
import com.schedule.entity.PlanWithStaff;
import com.schedule.entity.Staff;
import com.schedule.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
