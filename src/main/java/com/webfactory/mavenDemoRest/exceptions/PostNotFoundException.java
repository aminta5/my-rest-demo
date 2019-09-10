package com.webfactory.mavenDemoRest.exceptions;

public class PostNotFoundException extends RuntimeException {
    private String id;
    private String title;
    public PostNotFoundException(String id, String title) {
        super("Post not found for " + id + title, null, true, false);
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
