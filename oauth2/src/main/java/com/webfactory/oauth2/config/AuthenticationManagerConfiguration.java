package com.webfactory.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
public class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("username").password("password").roles("USER");
    }
}
