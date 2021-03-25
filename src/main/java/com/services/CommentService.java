package com.services;

import com.entities.Comment;
import com.exceptions.TicketNotFoundException;

import java.util.List;

public interface CommentService {

    // READ all comments for ticket
    List<Comment> getAllCommentsForTicket(int tid) throws TicketNotFoundException;
    // CREATE comment
    Comment createComment(String displayName, String comment, int tid) throws TicketNotFoundException;
}
