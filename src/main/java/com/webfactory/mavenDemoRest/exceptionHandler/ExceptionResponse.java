package com.webfactory.mavenDemoRest.exceptionHandler;


public class ExceptionResponse {
    //private String status;
    private String message;
    private String details;
    private String httpCodeMessage;

    ExceptionResponse(String message, String details, String httpCodeMessage) {
        super();
        //this.status = status;
        this.message = message;
        this.details = details;
        this.httpCodeMessage = httpCodeMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String detailes) {
        this.details = detailes;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }

    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/
}