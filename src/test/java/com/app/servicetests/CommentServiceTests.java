package com.app.servicetests;

import com.entities.Comment;
import com.entities.Priority;
import com.entities.Ticket;
import com.repos.CommentRepo;
import com.repos.TicketRepo;
import com.services.CommentService;
import com.services.CommentServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CommentServiceTests {

    static CommentService commentService;


    @Mock
    static CommentRepo commentRepo = Mockito.mock(CommentRepo.class);
    @Mock
    static TicketRepo ticketRepo = Mockito.mock(TicketRepo.class);

    static List<Comment> commentTestList;
    static Comment testComment;
    static Ticket testTicket;

    @BeforeAll
    static void setup() {
        commentService = new CommentServiceImpl(commentRepo, ticketRepo);

        commentTestList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            Comment comment = new Comment();
            if ((i % 2) == 0)
                comment.setTechId(1);
            else
                comment.setClientId(100);

            comment.setTicketId(1);
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

        testTicket = new Ticket();
        testTicket.setTicketId(1);
        testTicket.setPriority(Priority.LOW);
        testTicket.setClientId(1);
        testTicket.setDescription("Dummy Ticket");
        testTicket.setEpochStart(System.currentTimeMillis());

        Mockito.when(commentRepo.findAllByTicketIdEquals(1)).thenReturn(commentTestList);
        Mockito.when(commentRepo.findById(1)).thenReturn(Optional.of(commentTestList.get(2)));
        Mockito.when(commentRepo.save(any(Comment.class))).thenReturn(testComment);
        Mockito.when(ticketRepo.findById(anyInt())).thenReturn(Optional.of(testTicket));
    }

    @Test
    void Test1() {
        try {
            List<Comment> comments = commentService.getAllCommentsForTicket(1);
            for (Comment c: comments) {
                Assertions.assertEquals(1, c.getTicketId());
            }
        } catch (Exception e) {
          e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    void Test2() {
        try {
            Comment comment = new Comment();
            comment.setTicketId(2);
            comment.setCommentId(0);
            comment.setCmnt("Dummy data");
            comment = commentService.createComment(comment,2);
            Assertions.assertNotEquals(0, comment.getEpochTime());
            Assertions.assertEquals(2,comment.getTicketId());
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
