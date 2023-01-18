package com.schedule.entity;

import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-18-14:57
 */
@Data
public class StaffWithPre extends Staff{
    private Preference DayPre;

    private Preference WorkTimePre;

    private Preference ShiftTimePre;
}
