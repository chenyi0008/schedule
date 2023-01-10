package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Staff;
import com.schedule.mapper.StaffMapper;
import com.schedule.service.StaffService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-09-23:34
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {
}
