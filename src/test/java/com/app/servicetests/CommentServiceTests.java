package com.app.servicetests;

import com.entities.Comment;
import com.repos.CommentRepo;
import com.repos.TicketRepo;
import com.services.CommentServiceImpl;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {

    @Mock
    static CommentRepo commentRepo;
    @Mock
    static TicketRepo ticketRepo;

    static CommentServiceImpl commentService;
    static List<Comment> commentTestList;
    static Comment testComment;

    @BeforeAll
    static void setup() {
        commentService = new CommentServiceImpl();
        commentTestList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Comment comment = new Comment();
            if ((i % 2) == 0)
                comment.setTechId(1);
            else
                comment.setClientId(100);

            comment.setCommentId(i);
            comment.setEpochTime(System.currentTimeMillis());
            commentTestList.add(comment);
        }
        testComment = new Comment();
        testComment.setTicketId(2);
        testComment.setEpochTime(System.currentTimeMillis());
        testComment.setCommentId(11);
        testComment.setTechId(1);
        testComment.setCmnt("Dummy Data");

        Mockito.when(commentRepo.findAllByTicketIdEquals(1)).thenReturn(commentTestList);
        Mockito.when(commentRepo.findById(3)).thenReturn(Optional.of(commentTestList.get(2)));
        Mockito.when(commentRepo.save(any(Comment.class))).thenReturn(testComment);
        commentService = new CommentServiceImpl();
    }

    @Test
    void Test1() {
        try {
            List<Comment> comments = commentService.getAllCommentsForTicket(1);
            for (Comment c: comments) {
                Assertions.assertEquals(1, c.getTicketId());
            }
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void Test2() {
        try {
            Comment comment = new Comment();
            comment.setTicketId(2);
            comment.getCommentId(0);
            comment.setCmnt("Dummy data");
            comment = commentService.createComment(comment);
            Assertions.assertEquals(git commit);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
