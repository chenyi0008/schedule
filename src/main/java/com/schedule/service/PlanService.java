package com.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.schedule.entity.PlanWithStaff;
import com.schedule.util.Information;

import java.util.List;

/**
 * @author akuya
 * @create 2023-02-01-18:41
 */
public interface PlanService extends IService<PlanWithStaff> {
    List<Information> getInformation(Long storeId);
}
