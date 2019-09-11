package com.webfactory.mavenDemoRest.exceptions;

public class NotValidUserException extends RuntimeException{
    public NotValidUserException(){
        super("Required fields must be provided");
    }
}
