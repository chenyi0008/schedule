package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author akuya
 * @create 2023-02-01-13:53
 */
@Data
@TableName(value = "plan_staff")
public class PlanWithStaff {

    private String staff;

    private String time;

    private String work;

    private Long staffId;

    private String Date;

    private Long id;

    private Long storeId;
}
