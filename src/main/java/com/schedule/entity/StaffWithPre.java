package com.schedule.entity;

import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-18-14:57
 */
@Data
public class StaffWithPre extends Staff{
    private String DayPre;

    private String WorkTimePre;

    private String ShiftTimePre;
}
