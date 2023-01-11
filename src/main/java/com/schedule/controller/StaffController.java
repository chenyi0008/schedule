package com.schedule.controller;

import com.schedule.common.R;
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
    public R<String> add(@RequestBody Staff staff){
        staffService.save(staff);
        return R.success("添加成功");
    }

    /**
     * 更新员工数据
     * @param staff
     */
    @PutMapping
    public R<String> updata(@RequestBody Staff staff) {
        staffService.updateById(staff);
        return R.success("更新员工数据成功");
    }

    /**
     * 删除员工
     * @param ids
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        staffService.removeByIds(ids);
        return R.success("删除成功");
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
     * 条件分页查询
     * @param page
     * @param pageSize
     * @param staffName
     * @param StoreName
     * @return
     */
    @GetMapping("/page")
    public List<Staff> page(int page,int pageSize,String staffName,String StoreName){

        return null;
    }




}
