package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Preference;
import com.schedule.mapper.PreferenceMapper;
import com.schedule.service.PreferenceService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-11-14:50
 */
@Service
public class PreferenceServiceImpl extends ServiceImpl<PreferenceMapper, Preference> implements PreferenceService {
}
