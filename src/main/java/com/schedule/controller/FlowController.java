package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.R;
import com.schedule.entity.Flow;
import com.schedule.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * flow
 */
@RestController
@RequestMapping("/schedule")
public class FlowController {

    @Autowired
    FlowService flowService;

    /**
     * 批量添加数据
     * @param flows
     * @return
     */
    @PutMapping
    public R<String> save(@RequestBody List<Flow> flows){
        flowService.saveBatch(flows);
        return R.msg("添加成功");
    }



    /**
     * 修改数据
     * @param flow
     * @return
     */
    @PostMapping
    public R<String> update(@RequestBody Flow flow){
        flowService.updateById(flow);
        return R.msg("修改成功");
    }

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        flowService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 条件查询
     * @param storeId
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping
    public R<List<Flow>> list(@RequestParam String storeId,String startDate,String endDate){
        LambdaQueryWrapper<Flow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Flow::getStoreId,storeId)
                .ge(Flow::getDate,startDate)
                .le(Flow::getDate,endDate);
        List<Flow> list = flowService.list(wrapper);
        return R.success(list);
    }

}
