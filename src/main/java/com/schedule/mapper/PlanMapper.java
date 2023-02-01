package com.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.entity.PlanWithStaff;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PlanMapper extends BaseMapper<PlanWithStaff> {
}
