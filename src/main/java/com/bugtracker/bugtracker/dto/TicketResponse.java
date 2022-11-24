package com.bugtracker.bugtracker.dto;

import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private List<Ticket> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
