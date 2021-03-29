package com.controllers;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dtos.TicketDto;
import com.entities.*;
import com.exceptions.LoginException;
import com.exceptions.TicketNotFoundException;
import com.exceptions.UserNotFoundException;
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
public class TechnicianController {

    private Logger logger = LogManager.getLogger(TechnicianController.class);

    @Autowired
    TechnicianService technicianService;

    @Autowired
    TicketService ticketService;

    @PostMapping("/tech/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    //@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> techLogin(@RequestBody Technician technician) {

        String jwt = null;
        try {
            jwt = JwtUtil.generateJwtForTech(technician.getUserName(), technician.getPassword());
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            String username = decodedJWT.getClaim("userName").asString();

            logger.info(username + " has logged on.");
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (UserNotFoundException | LoginException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);

        } catch (JWTVerificationException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/tech")
    @ResponseBody
    public ResponseEntity<Object> getTechnician(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "name", required = false) String name) {
        List<Technician> technicians = new ArrayList<>();
        Technician tech;
        if (id != null && name != null) {
            tech = technicianService.getTech(name);
            if (tech.getId().equals(id))
                technicians.add(tech);
        } else if ((id == null) && (name != null))
            technicians.add(technicianService.getTech(name));

        else if ((name == null) && (id != null))
            technicians.add(technicianService.getTechnicianById(id));

        else if ((name == null) && (id == null))
            technicians = technicianService.getAllTechnicians();

        if (technicians.isEmpty())
            return new ResponseEntity<>("Technician not Found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(technicians, HttpStatus.ACCEPTED);
    }

    @GetMapping("/tech/ticket")
    @ResponseBody
    public ResponseEntity<Object> getAllTicketsOfTech(@RequestParam(value = "id", required = false) Integer techId, @RequestParam(value = "name", required = false) String name) {
        List<TicketDto> dtos = new ArrayList<>();
        if ((name != null) && (techId == null)) {
            List<Ticket> ticketList = technicianService.getAllTicketsOfTech(name);
            for (Ticket t : ticketList){
                dtos.add(new TicketDto(t));
            }
        } else if ((name == null) && (techId != null)) {
            List<Ticket> ticketList = technicianService.getAllTicketsOfTech(techId);
            for (Ticket t : ticketList){
                dtos.add(new TicketDto(t));
            }
        } else {
            List<Ticket> ticketList = technicianService.getAllTicketsOfTech(name);
            for (Ticket t : ticketList){
                dtos.add(new TicketDto(t));
            }
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/tech/ticket")
    @ResponseBody
    public ResponseEntity<Object> assignTicket(@RequestBody TechTickPK techTickPK, @RequestHeader("Authorization") String jwt) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JwtUtil.isValidJWT(jwt);

        Integer id = decodedJWT.getClaim("id").asInt();
        TechTicket techTicket = null;
        if (techTickPK.getTechId().equals(id)) {
            Technician technician = technicianService.getTechnicianById(id);
            techTicket = technicianService.assignTicketToSelf(technician, techTickPK.getTicketId());
            logger.info("Assigned ticket " + techTickPK.getTicketId() + " to " + technician.getDisplayName());
            return new ResponseEntity<>(techTicket.getPk() ,HttpStatus.CREATED);

        } else if (!techTickPK.getTechId().equals(id) && decodedJWT.getClaim("role").asString().equals("ADMIN")) {

            Technician technician = technicianService.getTechnicianById(id);
            logger.info("An Admin assigned ticket " + techTickPK.getTicketId() + " to " + technician.getDisplayName());
            techTicket = technicianService.assignTicketToOther((Admin) technician, techTickPK.getTechId(), techTickPK.getTicketId());
            return new ResponseEntity<>(techTicket.getPk() ,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Cannot assign ticket", HttpStatus.FORBIDDEN);
        }
        } catch (JWTVerificationException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TicketNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/tech/ticket")
    @ResponseBody
    public ResponseEntity<Object> modifyTicketStatus(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt, @RequestParam(value = "closed", required = false) boolean closed) {
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        try {
            if (decodedJWT.getClaim("role").asString().equals("ADMIN")) {
                List<Ticket> allTickets = ticketService.getAllTicket();
                for (Ticket t : allTickets) {
                    if (t.getTicketId().equals(ticket.getTicketId())) {
                        if (closed) {
                            TicketDto ticketDto = new TicketDto(technicianService.closeTicket(ticket));
                            logger.info("Ticket " + ticket.getTicketId() + " has been closed by " + technicianService.getTechnicianById(decodedJWT.getClaim("id").asInt()));
                            return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);
                        } else {
                            TicketDto ticketDto = new TicketDto(technicianService.escalateTicketStatus(ticket));
                            logger.info("Ticket " + ticket.getTicketId() + " has been escalated by " + technicianService.getTechnicianById(decodedJWT.getClaim("id").asInt()));
                            return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);
                        }
                    }
                }
            }
            List<Ticket> tickets = technicianService.getAllTicketsOfTech(decodedJWT.getClaim("id").asInt());
            for (Ticket t : tickets) {
                if (t.getTicketId().equals(ticket.getTicketId())) {
                    if (closed) {
                        TicketDto ticketDto = new TicketDto(technicianService.closeTicket(ticket));
                        logger.info("Ticket " + ticket.getTicketId() + " has been closed by " + technicianService.getTechnicianById(decodedJWT.getClaim("id").asInt()));
                        return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);
                    } else {
                        TicketDto ticketDto = new TicketDto(technicianService.escalateTicketStatus(ticket));
                        logger.info("Ticket " + ticket.getTicketId() + " has been escalated by " + technicianService.getTechnicianById(decodedJWT.getClaim("id").asInt()));
                        return new ResponseEntity<>(ticketDto, HttpStatus.ACCEPTED);
                    }
                }
            }
            throw new TicketNotFoundException("Ticket not found");
        } catch (JWTVerificationException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (TicketNotFoundException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
