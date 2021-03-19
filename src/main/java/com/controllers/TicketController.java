package com.controllers;

import com.entities.Ticket;
import com.exceptions.TicketNotFoundException;
import com.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;


    @GetMapping("/tickets")
    @ResponseBody
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTicket();

    }

    @GetMapping("/tickets/{ticketId}")
    @ResponseBody
    public Ticket getTicketById(@PathVariable int ticketId) {
        try {
            return ticketService.getTicketById(ticketId);
        } catch (TicketNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/tickets/client/{clientId}")
    @ResponseBody
    public List<Ticket> getTicketsByClientId(@PathVariable int clientId) {
        return ticketService.getAllTicketsByClientId(clientId);
    }

    @PostMapping("/tickets")
    @ResponseBody
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/tickets/{id}")
    @ResponseBody
    public Ticket updateTicket(@RequestBody Ticket ticket) {
        try {
            return ticketService.updateTicket(ticket);
        } catch (TicketNotFoundException e) {
            return null;
        }
    }
}
