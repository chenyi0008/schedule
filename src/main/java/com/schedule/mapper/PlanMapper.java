package com.schedule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.schedule.entity.PlanWithStaff;
import com.schedule.util.Information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface PlanMapper extends BaseMapper<PlanWithStaff> {

    @Select("SELECT *,t4.name as group_name from t_group t4 right join \n" +
            "(SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t( SELECT *,id as sta_id,name as staff_name FROM t_staff WHERE store_id = #{storeId} ) t1\n" +
            "\tleft JOIN staff_group t2 ON t1.id = t2.staff_id) t3\n" +
            "\ton t4.id = t3.group_id;")
    List<Information> getInformation(Long storeId);


}
