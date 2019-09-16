package com.webfactory.mavenDemoRest.exceptionHandler;

public class ExceptionResponse {
    private String message;
    private String description;

    ExceptionResponse(String message, String description) {
        super();
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}