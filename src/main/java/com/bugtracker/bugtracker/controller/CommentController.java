package com.bugtracker.bugtracker.controller;

import com.bugtracker.bugtracker.dto.CommentDto;
import com.bugtracker.bugtracker.dto.TicketDto;
import com.bugtracker.bugtracker.entity.Comment;
import com.bugtracker.bugtracker.entity.Ticket;
import com.bugtracker.bugtracker.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/members/{mid}/projects/{pid}/tickets/{tid}/comments")
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid){
        commentService.addCommentToTicket(commentDto,id,tid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/members/{mid}/projects/{pid}/tickets/{tid}/comments")
    public List<Comment> getAllCommentsByTicketId(@PathVariable(name = "pid") int id, @PathVariable(name = "tid") int tid){
        return commentService.getCommentsForTicket(id,tid);
    }
}
