package com.webfactory.mavenDemoRest.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token does not exist");
    }
}
