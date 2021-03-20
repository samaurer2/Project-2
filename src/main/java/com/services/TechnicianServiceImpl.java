package com.services;

import com.entities.Admin;
import com.entities.Technician;
import com.entities.Ticket;
import com.repos.TechTicketRepo;
import com.repos.TechnicianRepo;
import com.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public TechnicianServiceImpl(TechnicianRepo technicianRepo){
        this.technicianRepo = technicianRepo;
    }

    @Override
    public Technician getTechnicianById(int techId) {
        return null;
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return null;
    }

    @Override
    public Technician getTech(String uname) {
        return this.technicianRepo.findTechnicianByUserName(uname);
    }

    @Override
    public List<Ticket> getAllTicketsOfTech(int techId){
        return null;
    }

    @Override
    public List<Ticket> getAllTicketsOfTech(String uname){
        return null;
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
