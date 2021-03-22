package com.app.servicetests;

import com.entities.*;
import com.repos.TechTicketRepo;
import com.repos.TechnicianRepo;
import com.repos.TicketRepo;
import com.services.TechnicianService;
import com.services.TechnicianServiceImpl;
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
import java.util.Random;

import static java.lang.Math.abs;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TechnicianServiceTest {

    static TechnicianService technicianService;


    @Mock
    static TechnicianRepo technicianRepo = Mockito.mock(TechnicianRepo.class);

    @Mock
    static TechTicketRepo techTicketRepo = Mockito.mock(TechTicketRepo.class);

    @Mock
    static TicketRepo ticketRepo = Mockito.mock(TicketRepo.class);

    static Technician testAdmin;
    static Technician testTechnician;
    static List<Technician> testTechList;
    static List<Ticket> testTicketList;
    static List<TechTicket> testTechTicketList;
    static Random random;

    @BeforeAll
    static void setup(){
        technicianService = new TechnicianServiceImpl(technicianRepo, ticketRepo, techTicketRepo);


        testAdmin = new Admin();
        testAdmin.setId(1);
        testAdmin.setUserName("Mr. Admin");
        testAdmin.setPassword("mockito");

        testTechnician = new Technician();
        testTechnician.setId(2);
        testTechnician.setUserName("Mr. Tech");
        testTechnician.setPassword("mockito");

        testTechList = new ArrayList<>();
        testTechList.add(testAdmin);
        testTechList.add(testTechnician);

        testTicketList = new ArrayList<>();
        for (int i = 1; i < 11; ++i) {
            Ticket ticket = new Ticket();
            ticket.setTicketId(i);
            ticket.setPriority(Priority.LOW);
            ticket.setClientId(i%2);
            ticket.setDescription("Dummy Data");
            ticket.setEpochStart(System.currentTimeMillis());
            testTicketList.add(ticket);
        }

        testTechTicketList = new ArrayList<>();

        for(int i = 0; i < 8; ++i){
            TechTicket techTicket = new TechTicket();
            TechTickPK pk = new TechTickPK();
            pk.setTechId(1);
            pk.setTicketId(i+1);
            techTicket.setPk(pk);
            testTechTicketList.add(techTicket);
        }
        random = new Random();
        Mockito.when(technicianRepo.findAll()).thenReturn(testTechList);
        Mockito.when(techTicketRepo.findAllByTechId(1)).thenReturn(testTechTicketList);
        Mockito.when(ticketRepo.findById(any())).thenReturn(Optional.of(new Ticket((abs((random.nextInt()%8)+1)),"Dummy Data",1)));
        Mockito.when(ticketRepo.findById(1)).thenReturn(Optional.of(new Ticket(1, "test", 1)));
        Mockito.when(technicianRepo.findTechnicianByUserName("Mr. Admin")).thenReturn(testAdmin);
        Mockito.when(technicianRepo.findTechnicianByUserName("Mr Tech")).thenReturn(testTechnician);
        Mockito.when(technicianRepo.findById(1)).thenReturn(Optional.of(testAdmin));
        Mockito.when(technicianRepo.findById(2)).thenReturn(Optional.of(testTechnician));
    }

    @Test
    public void get_tech(){

        Technician technician = this.technicianService.getTech("Mr. Admin");
        Assertions.assertEquals("Mr. Admin", technician.getUserName());
        System.out.println(technician);
    }

    @Test

    public void getTechById(){
        Technician technician = technicianService.getTechnicianById(1);
        Assertions.assertEquals(1, technician.getId());
    }

    @Test
    public void getAllTechs(){
        List<Technician> technicians = technicianService.getAllTechnicians();
        Assertions.assertTrue(technicians.size()>1);
    }

    @Test
    void getAllTicketsOfTechId(){
        List<Ticket> tickets = technicianService.getAllTicketsOfTech(testAdmin.getId());
        List<Integer> ticketIds = new ArrayList<>();
        for(TechTicket tt:testTechTicketList) {
            if(tt.getPk().getTechId() == testAdmin.getId()){
                ticketIds.add(tt.getPk().getTicketId());
            }
        }
        for (Ticket t:tickets) {
            Assertions.assertTrue(ticketIds.contains(t.getTicketId()));
        }
    }

    @Test
    void getAllTicketsofTechName() {
        List<Ticket> tickets = technicianService.getAllTicketsOfTech(testAdmin.getUserName());
        List<Integer> ticketIds = new ArrayList<>();
        for (TechTicket tt : testTechTicketList) {
            if (tt.getPk().getTechId() == testAdmin.getId()) {
                ticketIds.add(tt.getPk().getTicketId());
            }
        }
        for (Ticket t : tickets) {
            Assertions.assertTrue(ticketIds.contains(t.getTicketId()));
        }
    }

    @Test
    public void close_ticket(){

        Ticket ticket = new Ticket("asdf", 1);
        ticket = technicianService.closeTicket(ticket);
        Assertions.assertEquals(Priority.CLOSED, ticket.getPriority());
        Assertions.assertNotNull(ticket.getEpochEnd());
    }
    @Test
    public void escalate_ticket(){

        Ticket ticket = new Ticket("asdf", 1);
        ticket.setTicketId(1);
        ticket = technicianService.escalateTicketStatus(ticket);
        Assertions.assertEquals(Priority.MEDIUM, ticket.getPriority());
    }
    @Test
    public void assign_to_self_test(){
        Ticket ticket = new Ticket("asdf", 1);
        ticket.setTicketId(1);
        TechTicket techTicket = technicianService.AssignTicketToSelf(testTechnician, ticket.getTicketId());

        Assertions.assertEquals(1, techTicket.getPk().getTicketId());
        Assertions.assertEquals(2, techTicket.getPk().getTechId());
    }

    @Test
    public void assign_to_other(){
        Ticket ticket = new Ticket("asdf", 1);
        ticket.setTicketId(1);
        TechTicket techTicket = technicianService.AssignTicketToOther((Admin)testAdmin, testTechnician.getId(), ticket.getTicketId());
        Assertions.assertEquals(1, techTicket.getPk().getTicketId());
        Assertions.assertEquals(2, techTicket.getPk().getTechId());
    }
}
