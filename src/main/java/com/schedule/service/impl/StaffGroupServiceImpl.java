package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Staff;
import com.schedule.entity.StaffGroup;
import com.schedule.mapper.StaffGroupMapper;
import com.schedule.service.StaffGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffGroupServiceImpl extends ServiceImpl<StaffGroupMapper, StaffGroup> implements StaffGroupService {

    @Autowired
    private StaffGroupMapper staffGroupMapper;

    public List<Staff> getListByGroupId(Long groupId) {
        return staffGroupMapper.getListByGroupId(groupId);
    }
}
