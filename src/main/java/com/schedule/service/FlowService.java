package com.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.schedule.entity.Flow;
import com.schedule.entity.Plan;

import java.util.List;

public interface FlowService extends IService<Flow> {

    public List<Plan> calculate(Long storeId, String startDate, String endDate);

}
