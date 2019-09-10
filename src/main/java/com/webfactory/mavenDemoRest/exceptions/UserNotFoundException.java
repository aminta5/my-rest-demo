package com.webfactory.mavenDemoRest.exceptions;

public class UserNotFoundException extends RuntimeException {
    private String id;
    private String nickname;
    public UserNotFoundException(String id, String nickname) {
        super("User not found for " + id + nickname, null, true, false);
        this.id = id;
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }
}
