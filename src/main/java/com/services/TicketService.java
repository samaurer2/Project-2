package com.services;

import com.entities.Ticket;
import com.exceptions.TicketNotFoundException;

import java.util.List;

public interface TicketService {


   public Ticket getTicketById(int id) throws TicketNotFoundException;
   public Ticket createTicket(Ticket ticket);
   public List<Ticket> getAllTicket();
   public List<Ticket> getAllTicketsByClientId(int clientId);
   public Ticket updateTicket(Ticket ticket) throws TicketNotFoundException;



}
