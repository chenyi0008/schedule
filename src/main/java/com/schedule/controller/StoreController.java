package com.schedule.controller;

import com.schedule.entity.tStore;
import com.schedule.service.tStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-22:52
 */
@RestController
@RequestMapping("/tstore")
public class tStoreController {

    @Autowired
    private tStoreService tstoreService;

    /**
     * 添加店铺
     */
    @PostMapping
    public void add(@RequestBody tStore tstore){
        tstoreService.save(tstore);

    }

    /**
     * 更新数据
     * @param tstore
     */
    @PutMapping
    public void updata(@RequestBody tStore tstore) {

        tstoreService.updateById(tstore);

    }

    /**
     * 删除
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        tstoreService.removeByIds(ids);

    }


    /**
     * 获取单行数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public tStore get(@PathVariable Long id) {

        tStore tstore = tstoreService.getById(id);

        return tstore;
    }



    /**
     * 获取所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<tStore> getAll(){
        List<tStore> list = tstoreService.list();
        return list;
    }



}
