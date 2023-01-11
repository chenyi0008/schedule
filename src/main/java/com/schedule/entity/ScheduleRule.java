package com.schedule.entity;

import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-11-15:01
 */
@Data
public class ScheduleRule {
    private Long id;

    private String ruleType;

    private Long storeId;

    private String value;
}
