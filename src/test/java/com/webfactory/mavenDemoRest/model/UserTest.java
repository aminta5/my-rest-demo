package com.webfactory.mavenDemoRest.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    User user;

    @Before
    public void setUp(){
        user = new User();
    }

    @Test
    public void getFirstName() {
        String firstname = "Jonny";
        user.setFirstName(firstname);
        assertEquals(firstname, user.getFirstName());
    }

    @Test
    public void getLastName() {
        String lastName = "smith";
        user.setLastName(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void getNickname() {
        String nickname = "Jojo";
        user.setNickname(nickname);
        assertEquals(nickname, user.getNickname());
    }

    @Test
    public void getEmail() {
        String email = "user@semanticsquare.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void getPassword() {
        String password = "lozinka";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void getLocation() {
        Location location = new Location();
        location.setId(1L);
        user.setLocation(location);
        assertEquals(location.getId(), user.getLocation().getId());

    }
}