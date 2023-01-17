package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Flow;
import com.schedule.mapper.FlowMapper;
import com.schedule.service.FlowService;
import org.springframework.stereotype.Service;

@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {
}
