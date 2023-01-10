package com.schedule.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.schedule.entity.Store;
import com.schedule.mapper.StoreMapper;
import com.schedule.service.StoreService;
import org.springframework.stereotype.Service;

/**
 * @author akuya
 * @create 2023-01-09-22:51
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
}
