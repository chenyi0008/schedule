package com.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.schedule.entity.Flow;

public interface FlowService extends IService<Flow> {

    public void calculate(Long storeId,String startDate,String endDate);

}
