package com.webfactory.mavenDemoRest.exceptions;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String message){
        super("No registered location for " + message);
    }
}
