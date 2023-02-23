package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Staff;
import com.schedule.mapper.StaffMapper;
import com.schedule.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-23:34
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {

    @Autowired
    public StaffMapper staffMapper;

    public List<Staff> getListByCondition(String name, Long storeId, Integer page, Integer size){
        Integer n = null, m = null;
        if(page != null && size != null){
            n = size;
            m = (page - 1) * size;
        }
        return staffMapper.getAllDate(storeId, name, m, n);
    }

}
