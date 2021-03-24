package com.controllers;

import com.entities.Ticket;
import com.exceptions.RequiredFieldsException;
import com.exceptions.TicketNotFoundException;
import com.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Component
@Controller
@CrossOrigin
public class TicketController {

    @Autowired
    TicketService ticketService;


    @GetMapping("/tickets")
    @ResponseBody
    public ResponseEntity<Object> getAllTickets() {
        return new ResponseEntity<>(ticketService.getAllTicket(), HttpStatus.OK);

    }

    @GetMapping("/tickets/{ticketId}")
    @ResponseBody
    public ResponseEntity<Object> getTicketById(@PathVariable int ticketId) {
        try {
            return new ResponseEntity<>(ticketService.getTicketById(ticketId), HttpStatus.OK);
        } catch (TicketNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tickets/client/{clientId}")
    //@ResponseBody
    public ResponseEntity<Object> getTicketsByClientId(@PathVariable int clientId) {
        return new ResponseEntity<>(ticketService.getAllTicketsByClientId(clientId), HttpStatus.OK);
    }

    @PostMapping("/tickets")
    @ResponseBody
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket) {
        try {
            return new ResponseEntity<>(ticketService.createTicket(ticket),HttpStatus.CREATED);
        } catch (RequiredFieldsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/tickets/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateTicket(@RequestBody Ticket ticket, @PathVariable int ticketId) {
        try {
            ticket.setTicketId(ticketId);
            return new ResponseEntity<>(ticketService.updateTicket(ticket), HttpStatus.ACCEPTED);
        } catch (TicketNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
