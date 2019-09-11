package com.webfactory.mavenDemoRest.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("User with this username already exists");
    }
}
