package com.schedule.entity;

import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-11-14:45
 */
@Data
public class PreferenceStaff {

    private Long id;

    private String preferenceType;

    private Long staffId;

    private String value;
}
