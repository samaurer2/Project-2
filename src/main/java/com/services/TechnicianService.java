package com.services;

import com.entities.Admin;
import com.entities.Technician;
import com.entities.Ticket;

import java.util.List;

public interface TechnicianService {

    //READ
    Technician getTech(String uname);
    Technician getTechnicianById(int techId);
    List<Technician> getAllTechnicians();
    List<Ticket> getAllTicketsOfTech(int techId);
    List<Ticket> getAllTicketsOfTech(String uname);
    Ticket AssignTicketToSelf(Technician technician, Ticket ticket);
    Ticket AssignTicketToOther(Admin admin, Technician technician, Ticket ticket);
    Ticket escalateTicketStatus(Ticket ticket);
    Ticket closeTicket(Technician technician);
}
