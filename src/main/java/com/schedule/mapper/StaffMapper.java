package com.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author akuya
 * @create 2023-01-09-23:33
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff> {

//    @Select("select t1.*, t2.name as group_name from " +
//            "(select t1.* ,t2.group_id from t_staff as t1 left outer join staff_group as t2 on t1.id = t2.staff_id where store_id = #{storeId})" +
//            " as t1 left outer join t_group as t2 on t1.group_id = t2.id ")

    public List<Staff> getAllDate(Long storeId, String staffName, Integer m, Integer n);

}
