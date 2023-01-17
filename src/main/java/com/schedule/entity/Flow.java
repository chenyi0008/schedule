package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Arrays;

@Data
@TableName(value = "t_flow")
public class Flow {
    private Long id;
    private String date;
    private Long storeId;
    private String value;

    public double[] getArr(){
        return Arrays.stream(value.split(",")).mapToDouble(Double::parseDouble).toArray();
    }
}
