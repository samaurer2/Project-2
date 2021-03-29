package com.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.entities.Comment;
import com.exceptions.RequiredFieldsException;
import com.exceptions.TicketNotFoundException;
import com.services.ClientService;
import com.services.CommentService;
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

import javax.websocket.server.PathParam;

@Component
@Controller
@CrossOrigin
public class CommentController {

    @Autowired
    TicketService ticketService;

    @Autowired
    TechnicianService technicianService;

    @Autowired
    ClientService clientService;

    @Autowired
    CommentService commentService;

    private Logger logger = LogManager.getLogger(CommentController.class);

    @GetMapping("/tickets/{tid}/comments")
    @ResponseBody
    public ResponseEntity<Object> getAllCommentsForTicketId(@PathVariable(name="tid") Integer ticketId) throws TicketNotFoundException {
        return new ResponseEntity<>(commentService.getAllCommentsForTicket(ticketId), HttpStatus.OK);
    }

    @PostMapping("/tickets/{tid}/comments")
    @ResponseBody
    public ResponseEntity<Object> createCommentForTicketId(@RequestBody Comment comment, @PathVariable("tid") Integer ticketId, @RequestHeader("Authorization") String jwt) throws TicketNotFoundException {
        try{
            DecodedJWT decodedJWT = JwtUtil.isValidJWT(jwt);
            if(!decodedJWT.getClaim("role").asString().isEmpty()){
                if(decodedJWT.getClaim("role").asString().equals("client")){
                    comment.setClientId(decodedJWT.getClaim("id").asInt());
                }else{
                    comment.setTechId(decodedJWT.getClaim("id").asInt());
                }
                comment.setTicketId(ticketId);
                logger.info("A new comment for ticket " + ticketId + " has been created.");
                return new ResponseEntity<>(commentService.createComment(comment, ticketId), HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }catch (JWTVerificationException e) {
            logger.warn(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
