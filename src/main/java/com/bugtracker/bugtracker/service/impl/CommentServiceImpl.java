package com.bugtracker.bugtracker.service.impl;

import com.bugtracker.bugtracker.dto.CommentDto;
import com.bugtracker.bugtracker.entity.Comment;
import com.bugtracker.bugtracker.entity.Project;
import com.bugtracker.bugtracker.entity.Ticket;
import com.bugtracker.bugtracker.exception.ResourceNotFoundException;
import com.bugtracker.bugtracker.repository.CommentRepository;
import com.bugtracker.bugtracker.repository.ProjectRepository;
import com.bugtracker.bugtracker.repository.TicketRepository;
import com.bugtracker.bugtracker.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final ProjectRepository projectRepository;
    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;

    public CommentServiceImpl(ProjectRepository projectRepository, CommentRepository commentRepository, TicketRepository ticketRepository) {
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
    }


    @Override
    public void addCommentToTicket(CommentDto commentDto, int projectId, int ticketId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project was not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticket was not found"));
        Comment comment = new Comment();
        comment.setMessage(commentDto.getMessage());
        comment.setTicket(ticket);
        commentRepository.save(comment);
        project.getTickets().get(ticketId).getComments().add(comment);
    }

    @Override
    public List<Comment> getCommentsForTicket(int projectId, int ticketId) {
        return projectRepository.findProjectById(projectId).getTickets().get(ticketId).getComments();
    }

    @Override
    public void editCommentToTicket(CommentDto commentDto, int projectId, int ticketId, int commentId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project was not found"));
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("ticket was not found"));
        Comment comment = project.getTickets().get(ticketId).getComments().get(commentId);
        comment.setMessage(commentDto.getMessage());
        comment.setTicket(ticket);
        commentRepository.save(comment);
        project.getTickets().get(ticketId).getComments().add(comment);
    }

    @Override
    public void deleteCommentToTicket(int projectId, int ticketId, int commentId) {
        projectRepository.findProjectById(projectId).getTickets().get(ticketId).getComments().remove(commentId);
    }

    private Integer convertToInteger(String num){
        return Integer.getInteger(num);
    }

}
