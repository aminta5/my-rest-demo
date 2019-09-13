package com.webfactory.mavenDemoRest.requestBodies;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestBodyPost {
    //private Long id;
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private Long userId;

    private RequestBodyLocation location;

    //getters and setters
    /*public Long getId() {
        return id;
    }*/

    /*public void setId(Long id) {
        this.id = id;
    }*/

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
