package com.bugtracker.bugtracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Integer id;
    private String name;
    private String description;
    private List<Integer> members;
}
