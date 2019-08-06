package com.webfactory.mavenDemoRest.requestBodies;


public class RequestBodyPost {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private RequestBodyLocation location;

    //constructors
    public RequestBodyPost() {
    }

    public RequestBodyPost(Long id, String title, String description, Long userId, RequestBodyLocation location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.location = location;
    }

    //getters and setetrs

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RequestBodyLocation getLocation() {
        return location;
    }

    public void setLocation(RequestBodyLocation location) {
        this.location = location;
    }
}
