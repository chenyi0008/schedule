package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "t_group")
public class Group {

    private Long id;

    private String name;

    private Long userId;

    private Long storeId;

}
