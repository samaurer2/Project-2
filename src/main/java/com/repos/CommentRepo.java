package com.repos;

import com.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepo extends CrudRepository<Comment, Integer> {


    public List<Comment> findAllByTicketIdEquals(int ticketId);
}
