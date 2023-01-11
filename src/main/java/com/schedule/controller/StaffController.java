package com.schedule.controller;

import com.schedule.entity.Staff;
import com.schedule.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-23:36
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    /**
     * 添加员工
     * @param staff
     */
    @PostMapping("/add")
    public void add(@RequestBody Staff staff){
        staffService.save(staff);

    }

    /**
     * 更新员工数据
     * @param staff
     */
    @PutMapping
    public void updata(@RequestBody Staff staff) {

        staffService.updateById(staff);

    }

    /**
     * 删除员工
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        staffService.removeByIds(ids);
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Staff get(@PathVariable Long id) {

        Staff staff = staffService.getById(id);

        return staff;
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<Staff> getAll(){
        List<Staff> list = staffService.list();
        return list;
    }
}
