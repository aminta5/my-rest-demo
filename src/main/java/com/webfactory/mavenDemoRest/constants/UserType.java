package com.webfactory.mavenDemoRest.constants;

public enum UserType {
    USER("user"),
    ADMIN("admin");

    private String name;

    private UserType(String name) {
        this.name = name;
    }
}
