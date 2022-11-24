package com.bugtracker.bugtracker.service;

import com.bugtracker.bugtracker.dto.CommentDto;
import com.bugtracker.bugtracker.entity.Comment;

import java.util.List;

public interface CommentService {

    void addCommentToTicket(CommentDto commentDto, int projectId, int ticketId);

    List<Comment> getCommentsForTicket(int projectId, int ticketId);

    void editCommentToTicket(CommentDto commentDto, int projectId, int ticketId, int commentId);

    void deleteCommentToTicket(int projectId,int ticketId,int commentId);
}
