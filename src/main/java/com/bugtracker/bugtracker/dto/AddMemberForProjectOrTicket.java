package com.bugtracker.bugtracker.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddMemberForProjectOrTicket {
    private List<Integer> members;
}
