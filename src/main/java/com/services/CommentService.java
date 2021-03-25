package com.services;

import com.entities.Comment;
import com.exceptions.TicketNotFoundException;

import java.util.List;

public interface CommentService {

    // READ all comments for ticket
    List<Comment> getAllCommentsForTicket(int tid) throws TicketNotFoundException;
    // CREATE comment
    Comment createComment(Comment comment, int tid) throws TicketNotFoundException;
}
