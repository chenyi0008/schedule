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

    /**
     * 三种类型：
     * 工作日偏好  1,3,4
     * 工作时间偏好  08:00-12:00,18:00-22:00
     * 班次时长偏好
     */
    private String preferenceType;

    private Long staffId;

    private String value;
}
