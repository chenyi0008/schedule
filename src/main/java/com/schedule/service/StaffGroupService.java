package com.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.schedule.entity.Staff;
import com.schedule.entity.StaffGroup;
import com.schedule.mapper.StaffGroupMapper;

import java.util.List;


public interface StaffGroupService extends IService<StaffGroup> {

    public List<Staff> getListByGroupId(Long groupId);

}
