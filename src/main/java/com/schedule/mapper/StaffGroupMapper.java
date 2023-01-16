package com.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.entity.Staff;
import com.schedule.entity.StaffGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StaffGroupMapper extends BaseMapper<StaffGroup> {

    /**
     *根据分组id获取员工名
     */
    @Select("select * from t_staff where id in (select staff_id from staff_group where group_id = group_id)")
    public List<Staff> getListByGroupId(Long groupId);

}
