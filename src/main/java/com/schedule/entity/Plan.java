package com.schedule.entity;

import lombok.Data;

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
    //
    private String[] job;

    public Plan(Integer startTime, Integer workTime, String date, String workType){
        this.job = new String[]{"门店经理", "副经理", "小组长", "收银", "导购", "库房"};
        this.startTime = startTime;
        this.workTime = workTime;
        this.date = date;
        this.workType = workType;
    }

    public Plan(Integer startTime, Integer workTime, String date, String workType, String[] arr){
        this.job = arr;
        this.startTime = startTime;
        this.workTime = workTime;
        this.date = date;
        this.workType = workType;
    }

}
