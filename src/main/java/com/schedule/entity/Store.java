package com.schedule.entity;

import lombok.Data;

/**
 * @author akuya
 * @create 2023-01-09-22:25
 */
@Data
@Table
public class tStore {

    private Long id;

    private String name;

    private String address;

    private double size;
}
