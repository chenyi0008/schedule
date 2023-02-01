package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.PlanWithStaff;
import com.schedule.mapper.PlanMapper;
import com.schedule.service.PlanService;
import org.springframework.stereotype.Service;


@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, PlanWithStaff> implements PlanService {
}
