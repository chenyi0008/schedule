package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.tStore;
import com.schedule.mapper.tStoreMapper;
import com.schedule.service.tStoreService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-09-22:51
 */
@Service
public class tStoreServiceImpl extends ServiceImpl<tStoreMapper,tStore> implements tStoreService {
}
