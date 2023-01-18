package com.schedule.entity;

import lombok.Data;

@Data
public class Plan {

    private Long staffId;

    private String startTime;

    private Integer workTime;

    private String date;

}
