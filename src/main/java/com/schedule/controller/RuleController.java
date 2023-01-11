package com.schedule.controller;

import com.schedule.common.R;
import com.schedule.entity.Rule;
import com.schedule.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * rule
 * @author akuya
 * @create 2023-01-11-15:06
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService scheduleRuleService;

    /**
     * 添加店铺规则
     * @param rule
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Rule rule){
        scheduleRuleService.save(rule);
        return R.success("添加成功");
    }

    /**
     * 更新店铺规则数据
     * @param rule
     */
    @PutMapping
    public R<String> updata(@RequestBody Rule rule) {

        scheduleRuleService.updateById(rule);
        return R.success("更新成功");
    }

    /**
     * 删除店铺规则
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        scheduleRuleService.removeByIds(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Rule> get(@PathVariable Long id) {

        Rule schedulerule = scheduleRuleService.getById(id);

        return R.success(schedulerule);
    }

    /**
     * 获取店铺规则所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Rule>> getAll(){
        List<Rule> list = scheduleRuleService.list();
        return R.success(list);
    }



}
