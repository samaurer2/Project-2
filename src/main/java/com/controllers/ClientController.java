package com.controllers;

import com.entities.Client;
import com.services.ClientService;
import com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Logger;

@Component
@Controller
public class ClientController {

    private Logger logger = Logger.getLogger("Client Login");

    @Autowired
    ClientService clientService;


    @PostMapping("/client/login")
    public String clientLogin(@RequestBody Client client){

        System.out.println(client);
        logger.info(client.toString());
        String jwt = JwtUtil.generateJwtForClient(client.getUserName(), client.getPassword());
        System.out.println(jwt);
        if(jwt != null){
            logger.info(client.getUserName() + " has logged on.");
            return jwt;
        }else{
            System.out.println(client);
            return null;
        }


    }

}
