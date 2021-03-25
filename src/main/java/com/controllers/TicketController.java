package com.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Priority;
import com.entities.Ticket;
import com.exceptions.RequiredFieldsException;
import com.exceptions.TicketNotFoundException;
import com.services.TechnicianService;
import com.services.TicketService;
import com.util.JwtUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

    @Autowired
    TechnicianService technicianService;

    private Logger logger = LogManager.getLogger(TicketController.class);

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
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt) {

        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            if (decodedJWT.getClaim("role").asString().equals("client")) {

                ticket.setTicketId(decodedJWT.getClaim("id").asInt());
                logger.info("Ticket " + ticket.getTicketId() + " has been created by " + decodedJWT.getClaim("userName"));
                return new ResponseEntity<>(ticketService.createTicket(ticket), HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (JWTVerificationException | RequiredFieldsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/tickets/{id}")
    @ResponseBody
    public ResponseEntity<Object> updateTicket(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt) {
        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            String role = decodedJWT.getClaim("role").asString();
            if (ticket.getPriority() != Priority.CLOSED) {
                if (role.equals("TECH") || role.equals("ADMIN")) {
                    ticket.setTicketId(decodedJWT.getClaim("id").asInt());
                    logger.info("Ticket " + ticket.getTicketId() + " has been updated by " + decodedJWT.getClaim("userName"));
                    return new ResponseEntity<>(ticketService.updateTicket(ticket), HttpStatus.ACCEPTED);

                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                if (role.equals("TECH") || role.equals("ADMIN")) {

                    ticket.setTicketId(decodedJWT.getClaim("id").asInt());
                    logger.info("Ticket " + ticket.getTicketId() + " has been updated by " + decodedJWT.getClaim("userName"));
                    return new ResponseEntity<>(technicianService.closeTicket(ticket), HttpStatus.ACCEPTED);

                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }


        } catch (TicketNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
