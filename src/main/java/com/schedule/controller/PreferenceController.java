package com.schedule.controller;

import com.schedule.common.R;
import com.schedule.entity.Preference;
import com.schedule.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * preference
 * @author akuya
 * @create 2023-01-11-14:51
 */
@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceStaffService;

    /**
     * 添加员工偏好
     * @param preference
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Preference preference){
        preferenceStaffService.save(preference);
        return R.success("添加成功");
    }

    /**
     * 更新员工偏好数据
     * @param preference
     */
    @PutMapping
    public R<String> updata(@RequestBody Preference preference) {

        preferenceStaffService.updateById(preference);
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
    public R<Preference> get(@PathVariable Long id) {

        Preference preference = preferenceStaffService.getById(id);

        return R.success(preference);
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Preference>> getAll(){
        List<Preference> list = preferenceStaffService.list();
        return R.success(list);
    }
}
