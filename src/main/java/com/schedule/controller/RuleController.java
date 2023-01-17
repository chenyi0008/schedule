package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    private RuleService ruleService;

    /**
     * 添加店铺规则
     * @param rule
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Rule rule){
        //如果有相同类型的规则，先删除，再添加
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Rule::getStoreId,rule.getStoreId());
        queryWrapper.eq(Rule::getRuleType,rule.getRuleType());
        ruleService.remove(queryWrapper);
        ruleService.save(rule);
        return R.msg("添加成功");
    }

    /**
     * 更新店铺规则数据
     * @param rule
     */
    @PutMapping
    public R<String> update(@RequestBody Rule rule) {
        ruleService.updateById(rule);
        return R.msg("更新成功");
    }

    /**
     * 删除店铺规则
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        ruleService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Rule> get(@PathVariable Long id) {

        Rule rule = ruleService.getById(id);

        return R.success(rule);
    }

    /**
     * 获取店铺规则所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Rule>> getAll(){
        List<Rule> list = ruleService.list();
        return R.success(list);
    }

    /**
     * 根据商店id获取数据
     * @param storeId
     * @return
     */
    @GetMapping
    public R<List<Rule>> getRuleByStoreId(Long storeId){
        LambdaQueryWrapper<Rule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(storeId != null,Rule::getStoreId,storeId);
        List<Rule> list = ruleService.list(queryWrapper);
        return R.success(list);
    }







}
