package com.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Client;
import com.services.ClientService;
import com.util.JwtUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



@Component
@Controller

public class ClientController {

    private Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;


//    @PostMapping("/client/login")
//    @ResponseBody
//    public String clientLogin(@RequestBody Client client){
//
//        String jwt = JwtUtil.generateJwtForClient(client.getUserName(), client.getPassword());
//        DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
//        System.out.println(decodedJWT);
//        String username = decodedJWT.getClaim("userName").asString();
//        if(username != null){
//            logger.info(username + " has logged on.");
//            return jwt;
//        }else{
//            System.out.println(client);
//            return null;
//        }
//
//
//    }

}
