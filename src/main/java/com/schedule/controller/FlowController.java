package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.schedule.common.R;
import com.schedule.entity.Flow;
import com.schedule.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
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

        List<String> list = new LinkedList<>();
        Long storeId = flows.get(0).getStoreId();

        for (Flow flow : flows) {
            String date = flow.getDate();
            list.add(date);
        }

        LambdaQueryWrapper<Flow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Flow::getStoreId, storeId)
                .in(Flow::getDate, list);

        flowService.remove(wrapper);
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
     * 根据id批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIds(@RequestParam List<Long> ids){
        flowService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 根据日期批量删除
     * @return
     */
    @DeleteMapping("/date")
    public R<String> deleteByDate(@RequestParam Long storeId,@RequestParam String startDate,@RequestParam String endDate){
        LambdaQueryWrapper<Flow> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Flow::getDate,startDate)
                .le(Flow::getDate,endDate)
                .eq(Flow::getStoreId,storeId);

        flowService.remove(wrapper);
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
    public R<List<Flow>> list(@RequestParam Long storeId,String startDate,String endDate){
        LambdaQueryWrapper<Flow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Flow::getStoreId, storeId)
                .ge(startDate != null, Flow::getDate, startDate)
                .le(endDate != null, Flow::getDate, endDate)
                .orderByAsc(Flow::getDate);;
        List<Flow> list = flowService.list(wrapper);
        return R.success(list);
    }




}
