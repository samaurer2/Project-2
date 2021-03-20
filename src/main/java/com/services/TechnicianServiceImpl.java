package com.services;

import com.entities.Admin;
import com.entities.TechTicket;
import com.entities.Technician;
import com.entities.Ticket;
import com.repos.TechTicketRepo;
import com.repos.TechnicianRepo;
import com.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class TechnicianServiceImpl  implements TechnicianService{

    @Autowired
    TechnicianRepo technicianRepo;

    @Autowired
    TicketRepo ticketRepo;

    @Autowired
    TechTicketRepo techTicketRepo;


    public TechnicianServiceImpl(){}

    public TechnicianServiceImpl(TechnicianRepo technicianRepo, TicketRepo ticketRepo, TechTicketRepo techTicketRepo) {
        this.technicianRepo = technicianRepo;
        this.ticketRepo = ticketRepo;
        this.techTicketRepo = techTicketRepo;
    }

    @Override
    public Technician getTechnicianById(int techId) {
        Technician technician = null;
        Optional op = technicianRepo.findById(techId);
        if(op.isPresent())
            technician = (Technician) op.get();
        return technician;
    }

    @Override
    public List<Technician> getAllTechnicians() {
        List<Technician> technicians = (List<Technician>) technicianRepo.findAll();
        return technicians;
    }

    @Override
    public Technician getTech(String uname) {
        return this.technicianRepo.findTechnicianByUserName(uname);
    }

    @Override
    public List<Ticket> getAllTicketsOfTech(int techId){
        List<TechTicket> techTickets = techTicketRepo.findAllByTechId(techId);
        List<Ticket> tickets = new ArrayList<>();
        for(TechTicket tt: techTickets){
            Ticket ticket = ticketRepo.findById(tt.getPk().getTicketId()).get();
            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public List<Ticket> getAllTicketsOfTech(String uname){
        Technician technician = technicianRepo.findTechnicianByUserName(uname);

        return getAllTicketsOfTech(technician.getId());
    }

    @Override
    public Ticket AssignTicketToSelf(Technician technician, Ticket ticket){
        return null;
    }


    @Override
    public Ticket AssignTicketToOther(Admin admin, Technician technician, Ticket ticket){
        return ticket;
    }

    @Override
    public Ticket escalateTicketStatus(Ticket ticket){
        return null;
    }

    @Override
    public Ticket closeTicket(Technician technician) {
        return null;
    }
}
