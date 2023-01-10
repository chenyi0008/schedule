package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Value;

/**
 * @author akuya
 * @create 2023-01-09-23:26
 */
@Data
@TableName(value = "t_staff")
public class Staff {

    private Long id;

    private String name;

    private String position;

    private String telephone;

    private String mailbox;

    private Long storeId;

}
