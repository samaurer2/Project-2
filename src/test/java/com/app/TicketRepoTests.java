package com.app;

import com.entities.Priority;
import com.entities.Ticket;
import com.repos.TicketRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TicketRepoTests {

    @Autowired
    TicketRepo ticketRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void createTicketTest(){
        Ticket ticket = new Ticket("A new problem has arrived!", 2);
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

    @Test
    void getAllTicketsTest(){
        List<Ticket> tickets = (ArrayList<Ticket>)ticketRepo.findAll();
        Assertions.assertTrue(tickets.size()>1);
    }

    @Test
    void getAllTicketsByClientId(){
        int clientId = 1;
        List<Ticket>tickets = (ArrayList<Ticket>)ticketRepo.findAllByClientIdEquals(clientId);
        for(Ticket t: tickets){
            Assertions.assertEquals(clientId, t.getClientId());
        }

        clientId = 2;
        tickets = (ArrayList<Ticket>)ticketRepo.findAllByClientIdEquals(clientId);
        for(Ticket t: tickets){
            Assertions.assertEquals(clientId, t.getClientId());
        }
    }
}
