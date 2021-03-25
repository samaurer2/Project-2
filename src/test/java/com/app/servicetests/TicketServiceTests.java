package com.app.servicetests;

import com.entities.Priority;
import com.entities.Ticket;
import com.exceptions.TicketNotFoundException;
import com.repos.TicketRepo;
import com.services.TicketService;
import com.services.TicketServiceImpl;
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
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketServiceTests {


    static TicketService ticketService;

    @Mock
    static TicketRepo ticketRepo = Mockito.mock(TicketRepo.class);

    static Ticket testTicket;
    static List<Ticket> testTicketList;

    @BeforeAll
    static void setUp() {
        ticketService = new TicketServiceImpl(ticketRepo);

        testTicket = new Ticket();
        testTicket.setTicketId(1);
        testTicket.setPriority(Priority.LOW);
        testTicket.setClientId(1);
        testTicket.setDescription("Default description");
        testTicketList = new ArrayList<>();
        testTicketList.add(testTicket);

        Ticket ticket;
        for (int i = 0; i < 50; ++i) {
            ticket = new Ticket();
            ticket.setTicketId(i + 2);
            ticket.setDescription("Default description " + i + 1);
            ticket.setClientId(i + 1);
            ticket.setPriority(Priority.values()[(i + 1) % 3]);
            testTicketList.add(ticket);
        }

        List<Ticket> otherTestList = new ArrayList<Ticket>();

        for(Ticket t: testTicketList) {
            if(t.getClientId() == 1)
                otherTestList.add(t);
        }
        Mockito.when(ticketRepo.findById(1)).thenReturn(java.util.Optional.of(testTicket));
        Mockito.when(ticketRepo.findById(100)).thenReturn(Optional.empty());
        Mockito.when(ticketRepo.findAll()).thenReturn(testTicketList);
        Mockito.when(ticketRepo.findAllByClientIdEquals(1)).thenReturn(otherTestList);
        Mockito.when(ticketRepo.save(any(Ticket.class))).thenReturn(testTicket);
    }

    @Test
    void getTicketByIdServiceTest() {
        try {
            Ticket ticket = ticketService.getTicketById(1);
            Assertions.assertEquals(1, ticket.getTicketId());

        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getAllTicketsServiceTest() {
        List<Ticket> tickets = ticketService.getAllTicket();
        Assertions.assertTrue(tickets.size()>1);
    }

    @Test
    void createTicketServiceTest() {
        try {
            Ticket ticket = new Ticket();
            ticket.setClientId(1);
            ticket.setDescription("New Ticket");
            ticket = ticketService.createTicket(ticket);
            Assertions.assertNotNull(ticket);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void updateTicketServiceTest() {
        Ticket ticket = new Ticket();
        ticket.setTicketId(1);
        ticket.setPriority(Priority.MEDIUM);
        try {
            ticket = ticketService.updateTicket(ticket);
            Assertions.assertEquals(Priority.MEDIUM, ticket.getPriority());
            Assertions.assertEquals(1, ticket.getTicketId());

        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void findTicketByIdExceptionTest() {
        try {
            Ticket ticket = ticketService.getTicketById(100);
            Assertions.fail();
        } catch (TicketNotFoundException e) {
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void getAllTicketByClientIdTest() {
            List<Ticket> tickets = ticketService.getAllTicketsByClientId(1);
            for(Ticket t:tickets) {
                Assertions.assertEquals(1, t.getClientId());
            }
    }

    @Test
    void updateTicketExceptionTest(){
        try {
            Ticket ticket = new Ticket();
            ticket.setTicketId(100);
            ticket = ticketService.updateTicket(ticket);
            Assertions.fail();
        } catch (TicketNotFoundException e) {
        } catch (Exception e) {
            Assertions.fail();
        }
    }

}
