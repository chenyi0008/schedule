package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.schedule.common.BaseContext;
import com.schedule.common.R;
import com.schedule.entity.Store;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * store
 * @author akuya
 * @create 2023-01-09-22:52
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 添加店铺
     */
    @PostMapping
    public R<String> add(@RequestBody Store store){
        Long userId = BaseContext.getUserId();
        store.setUserId(userId);
        storeService.save(store);
        return R.msg("添加成功");
    }

    /**
     * 更新店铺数据
     * @param store
     */
    @PutMapping
    public R<String> update(@RequestBody Store store) {

        storeService.updateById(store);
        return R.msg("更新成功");
    }

    /**
     * 删除店铺
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        storeService.removeByIds(ids);
        return R.msg("删除成功");
    }


    /**
     * 获取店铺单行数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Store> get(@PathVariable Long id) {

        Store store = storeService.getById(id);

        return R.success(store);
    }



    /**
     * 获取店铺所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Store>> getAll(){
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Store::getUserId,userId);
        List<Store> list = storeService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 分页条件查询
     * @param page
     * @param pageSize
     * @param storeName
     * @param address
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String storeName,String address){
        Long userId = BaseContext.getUserId();
        Page<Store> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Store::getUserId,userId);
        queryWrapper.like(storeName != null,Store::getName,storeName);
        queryWrapper.like(address!=null,Store::getAddress,address);
        Page<Store> list = storeService.page(pageInfo, queryWrapper);
        return R.success(list);
    }





}
