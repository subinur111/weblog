package com.subinuer.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class UsernamePasswordNullException extends AuthenticationException {
    // public UsernamePasswordNullException(){
    //     super();
    // }

    public UsernamePasswordNullException(String message){
        super(message);
    }

    public UsernamePasswordNullException(String message, Throwable ex){
        super(message, ex);
    }
}
