package com.controllers;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Technician;
import com.services.TechnicianService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Component
@Controller
public class TechnicianController {

    private Logger logger = Logger.getLogger("Tech Login");

    @Autowired
    TechnicianService technicianService;

    @PostMapping("/tech/login")
    @ResponseBody
    public String techLogin(@RequestBody Technician technician){

        String jwt = JwtUtil.generateJwtForTech(technician.getUserName(), technician.getPassword());
        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
        System.out.println(decodedJWT);
        String username = decodedJWT.getClaim("userName").asString();
        if(username != null){
            logger.info(username + " has logged on.");
            return jwt;
        }else{
            System.out.println(technician);
            return null;
        }
    }

}
