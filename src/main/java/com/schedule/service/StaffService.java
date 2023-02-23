package com.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.schedule.entity.Staff;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-23:33
 */
public interface StaffService extends IService<Staff> {
    public List<Staff> getListByCondition(String name, Long storeId, Integer page, Integer size);
}
