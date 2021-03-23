package com.controllers;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.*;
import com.exceptions.LoginException;
import com.exceptions.UserNotFoundException;
import com.services.TechnicianService;
import com.util.JwtUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
@Controller
public class TechnicianController {

    private Logger logger = LogManager.getLogger(TechnicianController.class);

    @Autowired
    TechnicianService technicianService;

    @PostMapping("/tech/login")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    //@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> techLogin(@RequestBody Technician technician){

        String jwt = null;
        try {
            jwt = JwtUtil.generateJwtForTech(technician.getUserName(), technician.getPassword());
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            System.out.println(decodedJWT);
            String username = decodedJWT.getClaim("userName").asString();

            logger.info(username + " has logged on.");
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(jwt, HttpStatus.OK);
            return responseEntity;

        } catch (UserNotFoundException e) {
            logger.warn(e.getMessage());
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            return responseEntity;

        } catch (LoginException e) {
            logger.warn(e.getMessage());
            ResponseEntity<Object> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
            return responseEntity;

        }

    }

    @GetMapping("/tech")
    @ResponseBody
    public ResponseEntity<Object> getTechnician(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "name", required = false) String name) {
        List<Technician> technicians = new ArrayList<>();
        if(id != null && name != null) {
            Technician tech = technicianService.getTech(name);
            if (tech.getId().equals(id))
                technicians.add(tech);
        }

        else if((id == null) && (name != null))
            technicians.add(technicianService.getTech(name));

        else if((name == null) && (id != null))
            technicians.add(technicianService.getTechnicianById(id));

        else if((name == null) && (id == null))
            technicians = technicianService.getAllTechnicians();

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(technicians, HttpStatus.ACCEPTED);
        return responseEntity;
    }

    @GetMapping("/tech/ticket")
    @ResponseBody
    public List<Ticket> getAllTicketsOfTech(@RequestParam(value = "id", required = false) Integer techId, @RequestParam(value = "name", required = false) String name) {
        List<Ticket> tickets = new ArrayList<>();
        if((name == null) && (techId == null)) {
            return tickets;
        } else if ((name != null) && (techId==null)) {
            return technicianService.getAllTicketsOfTech(name);
        } else if ((name == null) && (techId!=null)) {
            return technicianService.getAllTicketsOfTech(techId);
        } else {
            return technicianService.getAllTicketsOfTech(name);
        }
    }

    @PostMapping("/tech/ticket")
    @ResponseBody
    public TechTicket assignTicket(@RequestBody TechTickPK techTicket, @RequestHeader("Authorization") String jwt) {
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        Integer id = decodedJWT.getClaim("id").asInt();

        if (techTicket.getTechId().equals(id)) {

            Technician technician = technicianService.getTechnicianById(id);
            logger.info("");
            return technicianService.AssignTicketToSelf(technician, techTicket.getTicketId());

        } else if (!techTicket.getTechId().equals(id) && decodedJWT.getClaim("role").asString().equals("ADMIN")) {

            Technician technician = technicianService.getTechnicianById(id);
            return technicianService.AssignTicketToOther((Admin) technician,techTicket.getTechId(), techTicket.getTicketId());

        } else {
            //return 403
            return null;
        }
    }

    @PutMapping("/tech/ticket")
    @ResponseBody
    public Ticket modifyTicketStatus(@RequestBody Ticket ticket, @RequestHeader("Authorization") String jwt, @RequestParam(value = "closed", required = false) boolean closed) {
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        List<Ticket> tickets = technicianService.getAllTicketsOfTech(decodedJWT.getClaim("id").asInt());

        for(Ticket t: tickets) {
            if(t.getTicketId().equals(ticket.getTicketId())) {
                if(closed) {
                    return technicianService.closeTicket(ticket);
                } else {
                    return technicianService.escalateTicketStatus(ticket);
                }
            }
        }
        return null;
    }

}
