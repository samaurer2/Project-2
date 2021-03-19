package com.app.repotests;

import com.entities.TechTickPK;
import com.entities.TechTicket;
import com.repos.TechTicketRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TechTicketRepoTests {

    @Autowired
    TechTicketRepo techTicketRepo;

    @Test
    void takeTicketTest(){
        TechTickPK pk = new TechTickPK(2,3);
        TechTicket teti = new TechTicket(pk);
        techTicketRepo.save(teti);
    }

    @Test
    void GetAllTicketsOfTech(){
        List<TechTicket> techTickets = techTicketRepo.findAllByTechId(2);
        for (TechTicket ticket: techTickets) {
            Assertions.assertEquals(2,ticket.getPk().getTechId());
            System.out.println(ticket);
        }
    }

    @Test
    void getAllTechsOfTicket(){
        List<TechTicket> techTickets = techTicketRepo.findAllByTicketId(3);
        for (TechTicket ticket: techTickets) {
            Assertions.assertEquals(3,ticket.getPk().getTicketId());
            System.out.println(ticket);
        }
    }
}
