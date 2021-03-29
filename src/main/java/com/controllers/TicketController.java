package com.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dtos.TicketDto;
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

import java.util.ArrayList;
import java.util.List;


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
        List<TicketDto> ticketDtoList = new ArrayList<>();
        List<Ticket> ticketList = ticketService.getAllTicket();
        for (Ticket t : ticketList){
            ticketDtoList.add(new TicketDto(t));
        }
        return new ResponseEntity<>(ticketDtoList, HttpStatus.OK);

    }

    @GetMapping("/tickets/{ticketId}")
    @ResponseBody
    public ResponseEntity<Object> getTicketById(@PathVariable int ticketId) {
        try {
            TicketDto ticketDto = new TicketDto(ticketService.getTicketById(ticketId));
            return new ResponseEntity<>(ticketDto, HttpStatus.OK);
        } catch (TicketNotFoundException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tickets/client/{clientId}")
    @ResponseBody
    public ResponseEntity<Object> getTicketsByClientId(@PathVariable int clientId) {
        List<TicketDto> ticketDtoList = new ArrayList<>();
        List<Ticket> ticketList = ticketService.getAllTicketsByClientId(clientId);
        for (Ticket t : ticketList){
            ticketDtoList.add(new TicketDto(t));
        }
        return new ResponseEntity<>(ticketDtoList, HttpStatus.OK);
    }

    @PostMapping("/tickets")
    @ResponseBody
    public ResponseEntity<Object> createTicket(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt) {

        try {
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            if (decodedJWT.getClaim("role").asString().equals("client")) {

                ticket.setClientId(decodedJWT.getClaim("id").asInt());
                logger.info("A new ticket has been created by " + decodedJWT.getClaim("userName").asString());
                ticket.setPriority(Priority.LOW);
                TicketDto ticketDto = new TicketDto(ticketService.createTicket(ticket));

                return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (JWTVerificationException | RequiredFieldsException e) {
            logger.warn(e.getMessage());
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
                    logger.info("Ticket " + ticket.getTicketId() + " has been updated by " + decodedJWT.getClaim("userName").asString());
                    TicketDto ticketDto = new TicketDto(ticketService.updateTicket(ticket));
                    return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);

                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                if (role.equals("TECH") || role.equals("ADMIN")) {
                    logger.info("Ticket " + ticket.getTicketId() + " has been updated by " + decodedJWT.getClaim("userName").asString());
                    TicketDto ticketDto = new TicketDto(ticketService.updateTicket(ticket));
                    return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);

                } else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }


        } catch (TicketNotFoundException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
