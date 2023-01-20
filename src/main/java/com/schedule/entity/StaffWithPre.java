package com.schedule.entity;

import lombok.Data;

import java.util.Arrays;

/**
 * @author akuya
 * @create 2023-01-18-14:57
 */
@Data
public class StaffWithPre extends Staff{
    private String DayPre;

    private String WorkTimePre;

    private String ShiftTimePre;

    private int dayWorkTime;

    private int weekWorkTime;

    private int preNum;

    public int[] getArrD(){
        return Arrays.stream(DayPre.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int[] getArrW(){
        int frontHour = Integer.parseInt(WorkTimePre.substring(0,2));
        int frontMin = Integer.parseInt(WorkTimePre.substring(3,5));
        if(frontMin>0)frontHour++;
        int rearwardsHour = Integer.parseInt(WorkTimePre.substring(6,8));
        return new int[]{frontHour,rearwardsHour};
    }

    public int getArrS(){
        return Integer.parseInt(ShiftTimePre);
    }


}
