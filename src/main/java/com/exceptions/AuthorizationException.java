package com.exceptions;

public class AuthorizationException extends Exception{

    public AuthorizationException(){
        super("JWT authorization failed.");
    }
}
