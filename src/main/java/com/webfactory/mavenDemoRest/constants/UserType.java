package com.webfactory.mavenDemoRest.constants;

public enum UserType {
    ROLE_USER("user"),
    ROLE_ADMIN("admin");

    private String name;

    private UserType(String name) {
        this.name = name;
    }
}
