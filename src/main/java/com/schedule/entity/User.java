package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName( value = "t_user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private Long staffId;

    private String username;

    private String password;

}
