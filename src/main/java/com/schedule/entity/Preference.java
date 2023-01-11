package com.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-11-14:45
 */
@Data
@TableName(value = "preference_staff")
public class Preference {

    private Long id;

    private String preferenceType;

    private Long staffId;

    private String value;
}
