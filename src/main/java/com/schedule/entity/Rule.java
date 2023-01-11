package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-11-15:01
 */
@Data
@TableName(value = "rule_store")
public class Rule {
    private Long id;

    private String ruleType;

    private Long storeId;

    private String value;
}
