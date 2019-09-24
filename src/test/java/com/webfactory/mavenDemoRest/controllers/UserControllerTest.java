package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyUserToUser;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.events.OnRegistrationSuccessEvent;
import com.webfactory.mavenDemoRest.exceptionHandler.CustomizedResponseEntityExceptionHandler;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserDaoService userDaoService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private VerificationTokenDaoService tokenDaoService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private RequestBodyUserToUser requestBodyUserToUser;

    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userDaoService,
                eventPublisher,
                tokenDaoService,
                messageSource,
                requestBodyUserToUser);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).build());
        users.add(User.builder().id(2L).build());

        when(userDaoService.findAllUsers()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        User user = User.builder().id(1L).build();
        when(userDaoService.findUserById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserNotFound() throws Exception {
        //User user = User.builder().id(1L).build();
        when(userDaoService.findUserById(anyLong())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser() throws Exception{
        RequestBodyUser requestBodyUser = new RequestBodyUser();
        requestBodyUser.setId(1L);

        when(userDaoService.saveUser(any())).thenReturn(requestBodyUserToUser.convert(requestBodyUser));

        mockMvc.perform(post("/users/new")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("id", "")
                .param("nickname", "gogo"))
                .andExpect(status().isForbidden());

    }

    @Test
    public void confirmRegistration() {
    }

    @Test
    public void updateUser() throws Exception{
        User user = User.builder().id(1L).build();
        User updateUserData = User.builder().nickname("wert").build();
        when(userDaoService.updateUser(updateUserData, 1L)).thenReturn(user);
        mockMvc.perform(put("/users/1"))
                .andExpect(status().isAccepted());

    }

    @Test
    public void resetPassword() {
    }
}