package com.webfactory.mavenDemoRest.exceptions;

public class UserNotFoundException extends RuntimeException {
    private String identifier;

    public UserNotFoundException(String identifier) {
        super("User not found for this identifier " + identifier);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
