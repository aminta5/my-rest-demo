package com.webfactory.mavenDemoRest.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String message) {
        super("Post not found for " + message);
    }
}
