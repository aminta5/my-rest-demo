package com.webfactory.mavenDemoRest.events;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;


public class OnRegistrationSuccessEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Locale locale;
    private User user;

    public OnRegistrationSuccessEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}