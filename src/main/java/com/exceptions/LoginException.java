package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LoginException extends Exception{

    public LoginException(){
        super("Incorrect credentials.");
    }
}
