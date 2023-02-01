package com.schedule.entity;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

@Data
public class Plan {

    //员工id
    private Long staffId;
    //开始工作时间
    private Integer startTime;
    //工作的时间长短
    private Integer workTime;
    //工作的日期
    private String date;
    //工作类型
    private String workType;
    //允许做这个工作的职务
    private String[] job;
    //允许做这个班次的人数
    private Integer num;
    //星期几
    private Integer weekDay;
    //允许做这个班次的名单
    private List<StaffWithPre> list;
    //日期编号
    private Integer day;
    //班次编号
    private Integer shift;
    //重复次数
    private Integer repeat;



    public Plan(Integer startTime, Integer workTime, String date, String workType, Integer weekDay){
        this.job = new String[]{"门店经理", "副经理", "小组长", "收银", "导购", "库房"};
        this.startTime = startTime;
        this.workTime = workTime;
        this.date = date;
        this.workType = workType;
        this.num = 0;
        this.weekDay = weekDay;
    }

    public Plan(Integer startTime, Integer workTime, String date, String workType, String[] arr, Integer weekDay){
        this.job = arr;
        this.startTime = startTime;
        this.workTime = workTime;
        this.date = date;
        this.workType = workType;
        this.num = 0;
        this.weekDay = weekDay;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "staffId=" + staffId +
                ", startTime=" + startTime +
                ", workTime=" + workTime +
                ", date='" + date + '\'' +
                ", workType='" + workType + '\'' +
                ", job=" + Arrays.toString(job) +
                ", num=" + num +
                ", weekDay=" + weekDay +
                ", list=" + list +
                ", day=" + day +
                ", shift=" + shift +
                '}';
    }
}
