package com.services;

import com.entities.Comment;
import com.entities.Ticket;
import com.exceptions.TicketNotFoundException;
import com.repos.CommentRepo;
import com.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    TicketRepo ticketRepo;

    public CommentServiceImpl(){}

    public CommentServiceImpl(CommentRepo commentRepo, TicketRepo ticketRepo) {
        this.commentRepo = commentRepo;
        this.ticketRepo = ticketRepo;
    }

    public CommentRepo getCommentRepo() {
        return commentRepo;
    }

    public void setCommentRepo(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public TicketRepo getTicketRepo() {
        return ticketRepo;
    }

    public void setTicketRepo(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public List<Comment> getAllCommentsForTicket(int tid) throws TicketNotFoundException {
        Optional<Ticket> op = ticketRepo.findById(tid);
        List<Comment> comments = null;
        if(op.isPresent()){
            comments = commentRepo.findAllByTicketIdEquals(tid);
            return comments;
        }
        else
            throw new TicketNotFoundException("Could not find ticket with specified id.");
    }

    @Override
    public Comment createComment(Comment comment, int tid) throws TicketNotFoundException {
        Optional<Ticket> op = ticketRepo.findById(tid);
        if(op.isPresent()){
            comment.setEpochTime(System.currentTimeMillis());
            commentRepo.save(comment);
            return comment;
        }
        else
            throw new TicketNotFoundException("Could not find ticket with specified id.");
    }
}
