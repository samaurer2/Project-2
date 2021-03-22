package com.controllers;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Admin;
import com.entities.TechTicket;
import com.entities.Technician;
import com.entities.Ticket;
import com.services.TechnicianService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public List<Technician> getAllTechnicians() {
        return technicianService.getAllTechnicians();
    }

    @GetMapping("/tech/{id}")
    @ResponseBody
    public Technician getTechnician(@PathVariable int id) {
        return technicianService.getTechnicianById(id);
    }

    @GetMapping("/tech/ticket{techId}")
    @ResponseBody
    public List<Ticket> getAllTicketsOfTech(int techId) {
        return null;
    }

    @PostMapping("/tech/ticket")
    public Ticket assignTicketToSelf(@RequestBody TechTicket techTicket) {
        return null;
    }

//    public Ticket assignTicketToOther(Admin admin, Technician technician, Ticket ticket) {
//        return null;
//    }

    @PutMapping("/tech/ticket")
    @ResponseBody
    public Ticket modifyTicketStatus(@RequestBody Ticket ticket) {
        return null;
    }


}
