package com.schedule.controller;

import com.schedule.entity.PreferenceStaff;
import com.schedule.service.PreferenceStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-11-14:51
 */
@RestController
@RequestMapping("/preferencestaff")
public class PreferenceStaffController {

    @Autowired
    private PreferenceStaffService preferenceStaffService;

    /**
     * 添加员工偏好
     * @param preferenceStaff
     */
    @PostMapping("/add")
    public void add(@RequestBody PreferenceStaff preferenceStaff){
        preferenceStaffService.save(preferenceStaff);

    }

    /**
     * 更新员工偏好数据
     * @param preferenceStaff
     */
    @PutMapping
    public void updata(@RequestBody PreferenceStaff preferenceStaff) {

        preferenceStaffService.updateById(preferenceStaff);

    }

    /**
     * 删除员工偏好
     * @param ids
     */
    @DeleteMapping
    public void delete(@RequestParam List<Long> ids) {
        preferenceStaffService.removeByIds(ids);
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public PreferenceStaff get(@PathVariable Long id) {

        PreferenceStaff preferenceStaff = preferenceStaffService.getById(id);

        return preferenceStaff;
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public List<PreferenceStaff> getAll(){
        List<PreferenceStaff> list = preferenceStaffService.list();
        return list;
    }
}
