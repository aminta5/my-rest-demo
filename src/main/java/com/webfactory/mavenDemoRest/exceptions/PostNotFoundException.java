package com.webfactory.mavenDemoRest.exceptions;

public class PostNotFoundException extends RuntimeException {
    private String identifier;

    public PostNotFoundException(String identifier) {
        super("Post not found for this identifier " + identifier);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
