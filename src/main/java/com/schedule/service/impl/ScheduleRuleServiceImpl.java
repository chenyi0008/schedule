package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Rule;
import com.schedule.mapper.RuleMapper;
import com.schedule.service.RuleService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-11-15:05
 */
@Service
public class ScheduleRuleServiceImpl extends ServiceImpl<RuleMapper, Rule> implements RuleService {
}
