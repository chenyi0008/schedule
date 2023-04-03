package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.schedule.common.BaseContext;
import com.schedule.common.R;
import com.schedule.entity.Store;
import com.schedule.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * store
 * @author akuya
 * @create 2023-01-09-22:52
 */
@Slf4j
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加店铺
     */
    @PostMapping
    public R<String> add(@RequestBody Store store){
        Long userId = BaseContext.getUserId();
        cacheUpdate();
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
        cacheUpdate();
        storeService.updateById(store);
        return R.msg("更新成功");
    }

    /**
     * 删除店铺
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        cacheUpdate();
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
        List<Store> list;
        Long userId = BaseContext.getUserId();
        String key = "/store/getAll:" + userId;
        list = (List<Store>) redisTemplate.opsForValue().get(key);
        if(list != null){
            log.info("redis key:[{}]", key);
            return R.success(list);
        }

        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Store::getUserId,userId);
        list = storeService.list(queryWrapper);
        redisTemplate.opsForValue().set(key, list);
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
        Page<Store> list;
        String key = "/store/page:" + BaseContext.getUserId();

//        if((storeName == null || storeName == "") && (address == null || address == "")){
//
//            list = (Page<Store>)redisTemplate.opsForValue().get(key);
//            if(list != null){
//                log.info("redis key:{}", key);
//                return R.success(list);
//            }
//        }

        Long userId = BaseContext.getUserId();
        Page<Store> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Store> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Store::getUserId,userId);
        queryWrapper.like(storeName != null && storeName != "", Store::getName, storeName);
        queryWrapper.like(address!=null && address != "", Store::getAddress, address);
        list = storeService.page(pageInfo, queryWrapper);

//        if((storeName == null || storeName == "") && (address == null || address == "")){
//            redisTemplate.opsForValue().set(key, list);
//            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
//        }

        return R.success(list);


    }

    private void cacheUpdate(){
        Long userId = BaseContext.getUserId();
        String key1 = "/store/page:" + userId;
        String key2 = "/store/getAll:" + userId;
        List <String> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);
        redisTemplate.delete(keys);
    }





}
