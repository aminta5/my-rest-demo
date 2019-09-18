package com.webfactory.mavenDemoRest.model;

import com.webfactory.mavenDemoRest.validation.ValidEmail;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordChange {
    @ValidEmail
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "no unique characters allowed")
    @Size(min = 8, max = 12)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
