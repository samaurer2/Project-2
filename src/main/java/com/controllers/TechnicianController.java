package com.controllers;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.*;
import com.services.TechnicianService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Controller
public class TechnicianController {

    private Logger logger = Logger.getLogger("Tech Login");

    @Autowired
    TechnicianService technicianService;

    @PostMapping("/tech/login")
    @ResponseBody
    public String techLogin(@RequestBody Technician technician) {

        String jwt = JwtUtil.generateJwtForTech(technician.getUserName(), technician.getPassword());
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        System.out.println(decodedJWT);
        String username = decodedJWT.getClaim("userName").asString();
        if (username != null) {
            logger.info(username + " has logged on.");
            return jwt;
        } else {
            System.out.println(technician);
            return null;
        }
    }

    @GetMapping("/tech")
    @ResponseBody
    public List<Technician> getTechnician(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "name", required = false) String name) {
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

        return technicians;
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
