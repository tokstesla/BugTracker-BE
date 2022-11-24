package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.AddMemberForProjectOrTicket;
import com.bugtracker.bugtracker.dto.TicketDto;
import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.entity.Ticket;
import com.bugtracker.bugtracker.repository.TicketRepository;
import com.bugtracker.bugtracker.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;
    private final TicketRepository ticketRepository;

    public TicketController(TicketService ticketService, TicketRepository ticketRepository) {
        this.ticketService = ticketService;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/members/{mid}/projects/{pid}/tickets")
    public ResponseEntity<?> createTicket(@Valid @RequestBody TicketDto ticketDto, @PathVariable(name = "pid") int id){
        ticketService.createTicket(ticketDto,id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/members/{mid}/projects/{pid}/tickets")
    public List<Ticket> getAllTicketsByProjectId(@PathVariable(name = "pid") int id){
        return ticketService.getAllTicketsForProject(id);
    }

    @GetMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<Ticket> getTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid){
        return new ResponseEntity<>(ticketService.getTicketById(id,tid),HttpStatus.OK);
    }

    @PatchMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<?> updateTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody TicketDto ticketDto){
            ticketService.editTicket(ticketDto,tid,id);
        return new ResponseEntity<>("ticket updated",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/members/{mid}/projects/{pid}/tickets/{tid}")
    public ResponseEntity<String> deleteTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid){
        ticketService.deleteTicketById(id,tid);
        return new ResponseEntity<>("ticket deleted successfully.", HttpStatus.OK);
    }

    @PostMapping("/members/{mid}/projects/{pid}/tickets/{tid}/add")
    public ResponseEntity<?> assignMemberToTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody AddMemberForProjectOrTicket members){
        ticketService.AddMemberToAssignedDevsForTicket(members,id,tid);
        return new ResponseEntity<>("assigned",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/members/{mid}/projects/{pid}/tickets/{tid}/remove")
    public ResponseEntity<?> removeMemberToTicket(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid, @RequestBody AddMemberForProjectOrTicket members){
        ticketService.removeMemberToAssignedDevsForTicket(members,id,tid);
        return new ResponseEntity<>("removed",HttpStatus.OK);
    }


}
