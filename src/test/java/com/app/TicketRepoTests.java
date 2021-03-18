package com.app;

import com.entities.Client;
import com.entities.Priority;
import com.entities.Ticket;
import com.repos.TicketRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TicketRepoTests {

    @Autowired
    TicketRepo ticketRepo;
    @Test
    void contextLoads() {
    }

    @Test
    void createTicketTest(){
        Ticket ticket = new Ticket("A new problem has arrived!", 1);
        ticket = ticketRepo.save(ticket);
        System.out.println(ticket);
        Assertions.assertNotNull(ticket.getTicketId());
        Assertions.assertEquals(Priority.LOW, ticket.getPriority());
    }
    @Test
    void getTicketIdTest(){

        Ticket ticket = ticketRepo.findById(1).get();
        Assertions.assertEquals(1,ticket.getTicketId());
    }

}
