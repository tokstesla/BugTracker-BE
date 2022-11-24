package com.bugtracker.bugtracker.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class TicketDto {
    private Integer id;
    private String title;
    private String description;
    private Integer author;
    private String status;
    private String date;
    private String priority;
    private String type;
    private List<Integer> assigned_devs;
}
