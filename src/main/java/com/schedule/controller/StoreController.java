package com.schedule.controller;

import com.schedule.common.R;
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
    public R<String> add(@RequestBody Store store){
        storeService.save(store);
        return R.success("添加成功");
    }

    /**
     * 更新店铺数据
     * @param store
     */
    @PutMapping
    public R<String> updata(@RequestBody Store store) {

        storeService.updateById(store);
        return R.success("更新成功");
    }

    /**
     * 删除店铺
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        storeService.removeByIds(ids);
        return R.success("删除成功");
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
        List<Store> list = storeService.list();
        return R.success(list);
    }



}
