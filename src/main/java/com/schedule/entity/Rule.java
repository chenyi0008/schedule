package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Arrays;

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

    public double[] fGetArr(){
        if(!value.equals("职位规则"))
        return Arrays.stream(value.split(",")).mapToDouble(Double::parseDouble).toArray();
        return new double[1];
    }

}
