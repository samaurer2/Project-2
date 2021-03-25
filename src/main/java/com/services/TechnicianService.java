package com.services;

import com.entities.*;
import com.exceptions.TicketNotFoundException;

import java.util.List;

public interface TechnicianService {

    //READ
    Technician getTech(String uname);
    Technician getTechnicianById(int techId);
    List<Technician> getAllTechnicians();
    List<Ticket> getAllTicketsOfTech(int techId);
    List<Ticket> getAllTicketsOfTech(String uname);
    TechTicket assignTicketToSelf(Technician technician, int ticketId) throws TicketNotFoundException;
    TechTicket assignTicketToOther(Admin admin, int techId, int ticketId) throws TicketNotFoundException;
    Ticket escalateTicketStatus(Ticket ticket);
    Ticket closeTicket(Ticket ticket);
}
