package com.services;

import com.entities.Ticket;
import com.exceptions.RequiredFieldsException;
import com.exceptions.TicketNotFoundException;
import com.repos.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepo ticketRepo;

    public TicketServiceImpl() {
    }

    public TicketServiceImpl(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Override
    public Ticket getTicketById(int id) throws TicketNotFoundException {
        Ticket ticket;
        Optional<Ticket> op = ticketRepo.findById(id);

        if (op.isPresent())
            ticket = op.get();
        else
            throw new TicketNotFoundException("Could not find ticket with specified id.");

        return ticket;
    }

    @Override
    public List<Ticket> getAllTicketsByClientId(int clientId) {
        return ticketRepo.findAllByClientIdEquals(clientId);

    }

    @Override
    public Ticket createTicket(Ticket ticket) throws RequiredFieldsException {
        if (ticket.getClientId() == null)
            throw new RequiredFieldsException("Client id cannot be null.");
        if (ticket.getDescription() == null)
            throw new RequiredFieldsException("Description cannot be null.");
        ticket.setEpochStart(System.currentTimeMillis());
        ticket = ticketRepo.save(ticket);
        return ticket;
    }

    @Override
    public List<Ticket> getAllTicket() {
        return (List<Ticket>) ticketRepo.findAll();
    }

    @Override
    public Ticket updateTicket(Ticket ticket) throws TicketNotFoundException{
        Optional<Ticket> op = ticketRepo.findById(ticket.getTicketId());
        if (!op.isPresent())
            throw new TicketNotFoundException("No ticket match id " +ticket.getTicketId());
        Ticket oldTicket = op.get();

        if (ticket.getDescription() != null)
            oldTicket.setDescription(ticket.getDescription());
        if (ticket.getPriority() != null)
            oldTicket.setPriority(ticket.getPriority());
        oldTicket = ticketRepo.save(oldTicket);
        return oldTicket;
    }
}
