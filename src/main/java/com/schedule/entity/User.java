package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@Data
@TableName( value = "t_user")
public class User  {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String password;

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
