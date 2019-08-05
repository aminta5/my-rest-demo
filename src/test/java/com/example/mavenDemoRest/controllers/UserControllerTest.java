/*
package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.daoServices.UserDaoService;
import com.example.mavenDemoRest.converters.LocationCommandToLocation;
import com.example.mavenDemoRest.converters.UserCommandToUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

public class UserControllerTest {
    @Mock
    UserDaoService userDaoService;
    @Mock
    UserCommandToUser userCommandToUser;
    @Mock
    LocationCommandToLocation locationCommandToLocation;

    UserController userController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userDaoService, userCommandToUser, locationCommandToLocation);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new Con).build();
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void getUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void createUser() {
    }

    @Test
    public void updateUser() {
    }
}*/
