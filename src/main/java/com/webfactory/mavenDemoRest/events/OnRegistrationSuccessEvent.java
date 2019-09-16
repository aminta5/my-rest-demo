package com.webfactory.mavenDemoRest.events;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;


public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private User user;

    public OnRegistrationSuccessEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}