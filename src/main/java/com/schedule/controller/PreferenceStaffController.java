package com.schedule.controller;

import com.schedule.common.R;
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
    public R<String> add(@RequestBody PreferenceStaff preferenceStaff){
        preferenceStaffService.save(preferenceStaff);
        return R.success("添加成功");
    }

    /**
     * 更新员工偏好数据
     * @param preferenceStaff
     */
    @PutMapping
    public R<String> updata(@RequestBody PreferenceStaff preferenceStaff) {

        preferenceStaffService.updateById(preferenceStaff);
        return R.success("更新成功");
    }

    /**
     * 删除员工偏好
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        preferenceStaffService.removeByIds(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<PreferenceStaff> get(@PathVariable Long id) {

        PreferenceStaff preferenceStaff = preferenceStaffService.getById(id);

        return R.success(preferenceStaff);
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<PreferenceStaff>> getAll(){
        List<PreferenceStaff> list = preferenceStaffService.list();
        return R.success(list);
    }
}
