package com.repos;

import com.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface CommentRepo extends CrudRepository<Comment, Integer> {


    public List<Comment> findAllByTicketIdEquals(int ticketId);
}
