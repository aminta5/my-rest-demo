package com.webfactory.mavenDemoRest.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String reason){
        super("User already exists " + reason);
    }
}
