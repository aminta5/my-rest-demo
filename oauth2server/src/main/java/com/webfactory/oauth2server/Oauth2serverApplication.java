package com.webfactory.oauth2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@SpringBootApplication
public class Oauth2serverApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2serverApplication.class, args);
    }
    /*@RequestMapping("/validateUser")
    public Principal user(Principal user) {
        return user;
    }*/

    @Configuration
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {


        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("filip-user").password("filip-pass").roles("USER");
        }

    }

}
