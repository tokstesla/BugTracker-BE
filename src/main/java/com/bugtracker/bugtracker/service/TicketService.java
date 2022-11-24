package com.bugtracker.bugtracker.service;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.TicketDto;
import com.bugtracker.bugtracker.entity.Ticket;

import java.util.List;

public interface TicketService {
    void createTicket(TicketDto ticketDto,int projectId);

    List<Ticket> getAllTicketsForProject(int projectId);

//    TicketResponse getAllTickets(int pageNo, int pageSize, String sortBy, String sortDir);

    List<Ticket> getAllTicketsByMemberId(int projectId, int memberId);

    Ticket getTicketById(int projectId,int ticketId);

    void editTicket(TicketDto ticketDto,int ticketId,int projectId);

    void deleteTicketById(int projectId,int ticketId);


    void AddMemberToAssignedDevsForTicket(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId, int ticketId);

    void removeMemberToAssignedDevsForTicket(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId, int ticketId);

}
