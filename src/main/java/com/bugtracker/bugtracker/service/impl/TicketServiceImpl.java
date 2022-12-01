package com.bugtracker.bugtracker.service.impl;

import com.bugtracker.bugtracker.dto.*;
import com.bugtracker.bugtracker.entity.*;
import com.bugtracker.bugtracker.entity.enums.Priority;
import com.bugtracker.bugtracker.entity.enums.Status;
import com.bugtracker.bugtracker.entity.enums.Type;
import com.bugtracker.bugtracker.exception.ResourceNotFoundException;
import com.bugtracker.bugtracker.repository.*;
import com.bugtracker.bugtracker.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final ProjectRepository projectRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;


    public TicketServiceImpl(TicketRepository ticketRepository, ProjectRepository projectRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public void createTicket(TicketDto ticketDto,int projectId) {
        Project project = projectRepository.findProjectById(projectId);
        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDate(new Date());
        ticket.setDescription(ticket.getDescription());
        ticket.setProject(project);
        ticket.setAuthor(memberRepository.findMemberById(ticketDto.getAuthor()));
        ticket.setType(Type.valueOf(ticketDto.getType()));
        ticket.setStatus(Status.valueOf(ticketDto.getStatus()));
        ticket.setPriority(Priority.valueOf(ticketDto.getPriority()));
        ticket.setComments(new ArrayList<>());
        ticket.setAssigned_devs(new ArrayList<>());
        ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAllTicketsForProject(int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("project not found"));
        return project.getTickets();
    }



    @Override
    public List<Ticket> getAllTicketsByMemberId(int projectId, int memberId) {
        List<Project> projects = projectRepository.findAll().stream().filter(project -> project.getMembers().stream().filter(member -> member.getId().equals(memberId)).isParallel()).toList();
        return projects.stream().map(Project::getTickets).flatMap(Collection::stream).collect(Collectors.toList());
    }


    @Override
    public Ticket getTicketById(int projectId, int ticketId){
        return projectRepository.findProjectById(projectId).getTickets().get(ticketId);
    }


    @Override
    public void AddMemberToAssignedDevsForTicket(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId, int ticketId) {
        List<Member> members = addMemberForProjectOrTicket.getMembers().stream().map(member -> memberRepository.findMemberById(member)).toList();
        Project project = projectRepository.findProjectById(projectId);
        project.getTickets().get(ticketId).setAssigned_devs(members);
        projectRepository.save(project);
    }

    @Override
    public void removeMemberToAssignedDevsForTicket(AddMemberForProjectOrTicket addMemberForProjectOrTicket, int projectId, int ticketId) {
        List<Member> removedMembers = addMemberForProjectOrTicket.getMembers().stream().map(id -> memberRepository.findMemberById(id)).toList();
        Project project = projectRepository.findProjectById(projectId);
        List<Member> members = project.getTickets().get(ticketId).getAssigned_devs().stream().filter(dev -> !removedMembers.contains(dev)).toList();
        project.getTickets().get(ticketId).setAssigned_devs(members);
        projectRepository.save(project);
    }

    @Override
    public void editTicket(TicketDto ticketDto,int ticketId, int projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project was not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticket was not found"));
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDate(new Date());
        ticket.setDescription(ticket.getDescription());
        ticket.setProject(project);
        ticket.setAuthor(memberRepository.findMemberById(ticketDto.getAuthor()));
        ticket.setType(Type.valueOf(ticketDto.getType()));
        ticket.setStatus(Status.valueOf(ticketDto.getStatus()));
        ticket.setPriority(Priority.valueOf(ticketDto.getPriority()));
        ticket.setComments(new ArrayList<>());
        ticket.setAssigned_devs(new ArrayList<>());
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicketById(int projectId, int ticketId) {
        projectRepository.findProjectById(projectId).getTickets().remove(ticketId);
    }

    private Integer convertToInteger(String num){
        return Integer.getInteger(num);
    }

}
