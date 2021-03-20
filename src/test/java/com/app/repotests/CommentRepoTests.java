package com.app.repotests;

import com.entities.Comment;
import com.repos.CommentRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentRepoTests {

    @Autowired
    CommentRepo commentRepo;

    @Test
    void createCommentTest(){

            Comment comment = new Comment();
            comment.setComment("A new Comment has Arrived");
            comment.setTicketId(4);
            comment.setEpochTime(System.currentTimeMillis());
            comment = commentRepo.save(comment);
            Assertions.assertNotEquals(null, comment.getCommentId());
    }
    @Test
    void getAllCommentsOfTicketTest(){
        List<Comment> comments = commentRepo.findAllByTicketIdEquals(1);
        for (Comment c:comments) {
            Assertions.assertEquals(1,c.getTicketId());
        }
    }
}
