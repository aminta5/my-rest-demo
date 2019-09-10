package com.webfactory.mavenDemoRest.exceptions;

public class LocationNotFoundException extends RuntimeException{
    private String city;
    public LocationNotFoundException(String city){
        super("No registered location for " + city);
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
