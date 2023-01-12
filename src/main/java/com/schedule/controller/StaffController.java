package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.schedule.common.R;
import com.schedule.entity.Staff;
import com.schedule.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * staff
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
    public R<String> add(@RequestBody Staff staff){
        staffService.save(staff);
        return R.msg("添加成功");
    }

    /**
     * 更新员工数据
     * @param staff
     */
    @PutMapping
    public R<String> updata(@RequestBody Staff staff) {
        staffService.updateById(staff);
        return R.msg("更新员工数据成功");
    }

    /**
     * 删除员工
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        staffService.removeByIds(ids);
        return R.msg("删除成功");
    }

    /**
     * 根据id获取单条数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Staff> get(@PathVariable Long id) {

        Staff staff = staffService.getById(id);

        return R.success(staff);
    }

    /**
     * 获取员工所有数据
     * @return
     */
    @GetMapping("/getAll")
    public R<List<Staff>> getAll(){
        List<Staff> list = staffService.list();
        return R.success(list);
    }

    /**
     * 分页条件查询
     * @param page
     * @param pageSize
     * @param staffName
     * @param storeId
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String staffName,String storeId){
        //分页构造器
        Page<Staff> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Staff> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(staffName != null, Staff::getName, staffName);
        queryWrapper.eq(storeId != null, Staff::getStoreId, storeId);
        Page<Staff> list = staffService.page(pageInfo, queryWrapper);
        return R.success(list);
    }
}
