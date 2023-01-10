package com.schedule.controller;

import com.schedule.entity.Staff;
import com.schedule.service.tStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-23:36
 */
@RestController
@RequestMapping("/tstaff")
public class tStaffController {

    @Autowired
    private tStaffService tstaffService;

    /**
     * 添加
     * @param tstaff
     */
    @PostMapping("/add")
    public void add(@RequestBody Staff tstaff){
        tstaffService.save(tstaff);

    }

    /**
     * 删除
     * @param tstaff
     */
    @PutMapping
    public void updata(@RequestBody Staff tstaff) {

        tstaffService.updateById(tstaff);

    }

    /**
     * 删除
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        tstaffService.removeByIds(ids);

    }

    /**
     * 根据id获取单条
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Staff get(@PathVariable Long id) {

        Staff tstaff = tstaffService.getById(id);

        return tstaff;
    }


    /**
     * 获取所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<Staff> getAll(){
        List<Staff> list = tstaffService.list();
        return list;
    }
}