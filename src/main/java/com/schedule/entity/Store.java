package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-09-22:25
 */
@Data
@TableName(value = "t_store")
public class Store {

    private Long id;

    private String name;

    private String address;

    private double size;
}
