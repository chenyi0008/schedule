package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-09-23:26
 */
@Data
@TableName(value = "t_staff")
public class Staff {

    private Long id;

    private String name;

    /**
     * 六种职位
     * 门店经理
     * 副经理
     * 小组长
     * 收银
     * 导购
     * 库房
     */
    private String role;

    private String telephone;

    private String mailbox;

    private Long storeId;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private String groupId;

}
