package com.webfactory.mavenDemoRest.exceptions;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message){
        super(message);
    }
}
