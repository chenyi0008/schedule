package com.schedule.entity;

import lombok.Data;

import java.util.Arrays;

/**
 * @author akuya
 * @create 2023-01-18-14:57
 */
@Data
public class StaffWithPre extends Staff implements Comparable<StaffWithPre>{
    //工作日偏好
    private String dayPre;
    //工作时间偏好
    private String workTimePre;
    //班次时长偏好
    private String shiftTimePre;

    private int[] dayWorkTime;

    private int[] weekWorkTime;

    private int preNum;

    private int num = 0;

    private boolean[][] sign;

    public int[] getArrD(){
        if(dayPre == null)return new int[]{0,1,2,3,4,5,6};
        return Arrays.stream(dayPre.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    public int[][] getArrW(){
        if(workTimePre == null)return new int[][]{ {0, 24} };
        String[] str = workTimePre.split(",");
        int len = str.length;
        int[][] res = new int[len][2];
        for (int i = 0; i < len; i++) {
            int[] workTime = Arrays.stream(str[i].split("-")).mapToInt(Integer::parseInt).toArray();
            res[i] = workTime;
        }
        return res;
    }

    public void setDayWorkTime(int[] dayWorkTime) {
        this.dayWorkTime = dayWorkTime;
    }

    public void setWeekWorkTime(int[] weekWorkTime) {
        this.weekWorkTime = weekWorkTime;
    }

    public int[] getArrS(){
        if(this.shiftTimePre == null)return new int[]{8,40};
        return Arrays.stream(this.shiftTimePre.split(",")).mapToInt(Integer::parseInt).toArray();
    }


    @Override
    public int compareTo(StaffWithPre o) {
        return this.num - o.num;
    }

    @Override
    public String toString() {
        return "StaffWithPre{" +
                ", name=" + getName() +
//                "dayPre='" + dayPre + '\'' +
//                ", workTimePre='" + workTimePre + '\'' +
//                ", shiftTimePre='" + shiftTimePre + '\'' +
//                ", dayWorkTime=" + Arrays.toString(dayWorkTime) +
//                ", weekWorkTime=" + Arrays.toString(weekWorkTime) +
//                ", preNum=" + preNum +
//                ", num=" + num +
//                ", sign=" + Arrays.toString(sign) +
                '}';
    }
}
