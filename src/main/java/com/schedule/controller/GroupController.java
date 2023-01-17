package com.schedule.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.schedule.common.BaseContext;
import com.schedule.common.R;
import com.schedule.entity.Group;
import com.schedule.entity.Staff;
import com.schedule.entity.StaffGroup;
import com.schedule.service.GroupService;
import com.schedule.service.StaffGroupService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *group
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private StaffGroupService staffGroupService;

    /**
     * 添加小组
     * @param group
     * @return
     */
    @PutMapping
    public R<String> add(@RequestBody Group group){
        Long userId = BaseContext.getUserId();
        group.setUserId(userId);
        groupService.save(group);
        return R.msg("添加成功");
    }

    /**
     * 修改信息
     * @param group
     * @return
     */
    @PostMapping
    public R<String> update(@RequestBody Group group){
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId,userId);
        queryWrapper.eq(Group::getId,group.getId());
        groupService.update(group,queryWrapper);
        return R.msg("修改成功");
    }

    /**
     * 删除小组
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId,userId);
        queryWrapper.eq(Group::getId,id);
        groupService.remove(queryWrapper);
        return R.msg("删除成功");
    }

    /**
     * 条件查询小组
     * @return
     */
    @GetMapping
    public R<List<Group>> list(Long storeId){
        Long userId = BaseContext.getUserId();
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getUserId,userId);
        queryWrapper.eq(storeId != null,Group::getStoreId,storeId);
        List<Group> list = groupService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 员工加入小组
     * @param groupId
     * @param staffIds
     * @return
     */
    @GetMapping("/join")
    public R<String> join(Long groupId,@RequestParam List<Long> staffIds){
        staffGroupService.removeByIds(staffIds);

        List<StaffGroup> list = new ArrayList<>();

        for (Long staffId : staffIds) {
            StaffGroup staffGroup = new StaffGroup();
            staffGroup.setStaffId(staffId);
            staffGroup.setGroupId(groupId);
            list.add(staffGroup);
        }

        staffGroupService.saveBatch(list);
        return R.msg("加入成功");
    }

    /**
     *根据分组id获取员工名单
     * @return
     */
    @GetMapping("/list")
    public R<List<Staff>> getListByGroupId(Long groupId){
        List<Staff> list = staffGroupService.getListByGroupId(groupId);
        return R.success(list);
    }







}
