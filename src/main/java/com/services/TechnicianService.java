package com.services;

import com.entities.*;

import java.util.List;

public interface TechnicianService {

    //READ
    Technician getTech(String uname);
    Technician getTechnicianById(int techId);
    List<Technician> getAllTechnicians();
    List<Ticket> getAllTicketsOfTech(int techId);
    List<Ticket> getAllTicketsOfTech(String uname);
    TechTicket AssignTicketToSelf(Technician technician, Ticket ticket);
    TechTicket AssignTicketToOther(Admin admin, Technician technician, Ticket ticket);
    Ticket escalateTicketStatus(Ticket ticket);
    Ticket closeTicket(Ticket ticket);
}
