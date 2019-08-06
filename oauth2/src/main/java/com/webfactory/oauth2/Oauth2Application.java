package com.webfactory.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;


@SpringBootApplication
public class Oauth2Application {

    public static void main(String[] args) {

        SpringApplication.run(Oauth2Application.class, args);
    }

}
