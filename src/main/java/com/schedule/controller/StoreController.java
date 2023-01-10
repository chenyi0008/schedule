package com.schedule.controller;

import com.schedule.entity.Store;
import com.schedule.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
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
    public void add(@RequestBody Store store){
        storeService.save(store);
    }

    /**
     * 更新店铺数据
     * @param store
     */
    @PutMapping
    public void updata(@RequestBody Store store) {

        storeService.updateById(store);

    }

    /**
     * 删除店铺
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        storeService.removeByIds(ids);

    }


    /**
     * 获取店铺单行数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Store get(@PathVariable Long id) {

        Store store = storeService.getById(id);

        return store;
    }



    /**
     * 获取店铺所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<Store> getAll(){
        List<Store> list = storeService.list();
        return list;
    }



}
