package com.services;


import com.entities.*;
import com.exceptions.TicketNotFoundException;
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

    public TechnicianServiceImpl(TicketRepo ticketRepo){
        this.ticketRepo = ticketRepo;
    }


    @Override
    public Technician getTechnicianById(int techId) {
        Technician technician = null;
        Optional<Technician> op = technicianRepo.findById(techId);
        if(op.isPresent())
            technician = op.get();
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
            Optional<Ticket> op = ticketRepo.findById(tt.getPk().getTicketId());
            if (op.isPresent()){
                tickets.add(op.get());
            }
        }

        return tickets;
    }

    @Override
    public List<Ticket> getAllTicketsOfTech(String uname){
        Technician technician = technicianRepo.findTechnicianByUserName(uname);

        return getAllTicketsOfTech(technician.getId());
    }

    @Override
    public TechTicket assignTicketToSelf(Technician technician, int ticketId) throws TicketNotFoundException{
        Optional<Ticket> op = ticketRepo.findById(ticketId);
        if (op.isPresent()) {
            Ticket ticket = op.get();
            TechTicket techTicket = new TechTicket(new TechTickPK(technician.getId(), ticket.getTicketId()));
            techTicketRepo.save(techTicket);
            return techTicket;
        }else{
            throw new TicketNotFoundException("Ticket not found.");
        }
    }

    @Override
    public TechTicket assignTicketToOther(Admin admin, int techId, int ticketId) throws TicketNotFoundException{
        Optional<Ticket> op = ticketRepo.findById(ticketId);
        if(op.isPresent()){
            Ticket ticket = op.get();
            Technician technician = technicianRepo.findById(techId).get();
            TechTicket techTicket = new TechTicket(new TechTickPK(technician.getId(), ticket.getTicketId()));
            techTicketRepo.save(techTicket);
            return techTicket;
        }else{
            throw new TicketNotFoundException("Ticket not found.");
        }
    }

    @Override
    public Ticket escalateTicketStatus(Ticket ticket){
        Optional<Ticket> op = ticketRepo.findById(ticket.getTicketId());
        if (op.isPresent()){
            ticket = op.get();
            if(ticket.getPriority() != Priority.CLOSED || ticket.getPriority() != Priority.HIGH){
                ticket.setPriority(Priority.values()[ticket.getPriority().ordinal() + 1]);
                ticketRepo.save(ticket);
                return ticket;

            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public Ticket closeTicket(Ticket ticket) {

        Optional<Ticket> op = ticketRepo.findById(ticket.getTicketId());
        if (op.isPresent()){
            ticket = op.get();
            if(ticket.getPriority() != Priority.CLOSED){
                ticket.setPriority(Priority.CLOSED);
                ticket.setEpochEnd(System.currentTimeMillis());
                ticketRepo.save(ticket);
                return ticket;

        }else{
                return null;
            }
        }else{
            return null;
        }

    }
}
