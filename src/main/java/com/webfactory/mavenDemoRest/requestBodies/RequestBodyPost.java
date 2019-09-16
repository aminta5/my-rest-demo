package com.webfactory.mavenDemoRest.requestBodies;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestBodyPost {
    //private Long id;
    @NotNull
    @Size(max = 120)
    private String title;

    @NotNull
    @Size(max = 1000)
    private String description;

    private Long userId;

    private RequestBodyLocation location;

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
