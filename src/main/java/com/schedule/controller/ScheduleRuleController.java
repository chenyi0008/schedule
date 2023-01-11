package com.schedule.controller;

import com.schedule.entity.PreferenceStaff;
import com.schedule.entity.ScheduleRule;
import com.schedule.service.ScheduleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-11-15:06
 */
@RestController
@RequestMapping("/schedulerule")
public class ScheduleRuleController {

    @Autowired
    private ScheduleRuleService scheduleRuleService;

    /**
     * 添加店铺规则
     * @param scheduleRule
     */
    @PostMapping("/add")
    public void add(@RequestBody ScheduleRule scheduleRule){
        scheduleRuleService.save(scheduleRule);

    }

    /**
     * 更新店铺规则数据
     * @param scheduleRule
     */
    @PutMapping
    public void updata(@RequestBody ScheduleRule scheduleRule) {

        scheduleRuleService.updateById(scheduleRule);

    }

    /**
     * 删除店铺规则
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        scheduleRuleService.removeByIds(ids);
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ScheduleRule get(@PathVariable Long id) {

        ScheduleRule schedulerule = scheduleRuleService.getById(id);

        return schedulerule;
    }

    /**
     * 获取店铺规则所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<ScheduleRule> getAll(){
        List<ScheduleRule> list = scheduleRuleService.list();
        return list;
    }



}
