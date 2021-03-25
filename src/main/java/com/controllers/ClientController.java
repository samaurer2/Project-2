package com.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Client;
import com.exceptions.LoginException;
import com.exceptions.UserNotFoundException;
import com.services.ClientService;
import com.util.JwtUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



@Component
@Controller
@CrossOrigin
public class ClientController {

    private Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;


    @PostMapping("/client/login")
    @ResponseBody
    public ResponseEntity<Object> clientLogin(@RequestBody Client client){

        DecodedJWT decodedJWT;
        try {
            String jwt = JwtUtil.generateJwtForClient(client.getUserName(), client.getPassword());
            decodedJWT = JwtUtil.isValidJWT(jwt);
            String username = decodedJWT.getClaim("userName").asString();

            logger.info(username + " has logged on.");

            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (UserNotFoundException | LoginException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (JWTVerificationException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

}
