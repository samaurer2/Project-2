package com.services;

import com.entities.Ticket;
import com.exceptions.RequiredFieldsException;
import com.exceptions.TicketNotFoundException;

import java.util.List;

public interface TicketService {


   Ticket getTicketById(int id) throws TicketNotFoundException;
   Ticket createTicket(Ticket ticket) throws RequiredFieldsException;
   List<Ticket> getAllTicket();
   List<Ticket> getAllTicketsByClientId(int clientId);
   Ticket updateTicket(Ticket ticket) throws TicketNotFoundException;



}
