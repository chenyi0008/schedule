package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.ScheduleRule;
import com.schedule.mapper.ScheduleRuleMapper;
import com.schedule.service.ScheduleRuleService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-11-15:05
 */
@Service
public class ScheduleRuleServiceImpl extends ServiceImpl<ScheduleRuleMapper, ScheduleRule> implements ScheduleRuleService {
}
