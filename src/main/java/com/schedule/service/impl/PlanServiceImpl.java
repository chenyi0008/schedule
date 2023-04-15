package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.PlanWithStaff;
import com.schedule.mapper.PlanMapper;
import com.schedule.service.PlanService;
import com.schedule.util.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, PlanWithStaff> implements PlanService {

    @Autowired
    PlanMapper planMapper;

    public List<Information> getInformation(Long storeId){
        return planMapper.getInformation(storeId);

    }

}
