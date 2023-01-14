package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.common.R;
import com.schedule.entity.Preference;
import com.schedule.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private PreferenceService preferenceService;

    /**
     * 添加员工偏好
     * @param preference
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Preference preference){
        String preferenceType = preference.getPreferenceType();
        Long staffId = preference.getStaffId();

        /**
         * 判断是否已有相同类型偏好
         */
        LambdaQueryWrapper<Preference> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Preference::getPreferenceType,preferenceType);
        queryWrapper.eq(Preference::getStaffId,staffId);

        List<Preference> list = preferenceService.list(queryWrapper);
        if (!list.isEmpty())return R.error("请勿重复添加相同类型偏好");

        preferenceService.save(preference);
        return R.msg("添加成功");
    }

    /**
     * 更新员工偏好数据
     * @param preference
     */
    @PutMapping
    public R<String> updata(@RequestBody Preference preference) {

        preferenceService.updateById(preference);
        return R.msg("更新成功");
    }

    /**
     * 删除员工偏好
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        preferenceService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<List<Preference>> get(@PathVariable Long id) {
        List<Preference> list = preferenceService.list();
        List<Preference> list2 =new ArrayList<>();
        for(Preference p:list){
            if(id==p.getStaffId()){
                list2.add(p);
            }
        }

        return R.success(list2);
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Preference>> getAll(){
        List<Preference> list = preferenceService.list();
        return R.success(list);
    }

    
}
