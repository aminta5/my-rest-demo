package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyUserToUser;
import com.webfactory.mavenDemoRest.exceptionHandler.CustomizedResponseEntityExceptionHandler;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.services.VerificationTokenService;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private VerificationTokenService tokenDaoService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private RequestBodyUserToUser requestBodyUserToUser;

    UserController userController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService,
                eventPublisher,
                tokenDaoService,
                messageSource,
                requestBodyUserToUser);

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomizedResponseEntityExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).build());
        users.add(User.builder().id(2L).build());
        Page<User> usersPage = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0,1);

        when(userService.getAllUsers(pageable)).thenReturn(usersPage);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        User user = User.builder().id(1L).build();
        when(userService.getUserById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(UserNotFoundException.class);
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
//        RequestBodyUser requestBodyUser = new RequestBodyUser();
//        requestBodyUser.setNickname("werto");
//        requestBodyUser.setPassword("password");
//        requestBodyUser.setEmail("gogo@webmail.com");
//        User user = requestBodyUserToUser.convert(requestBodyUser);
        User user = User.builder().nickname("werto").password("password").email("dodo@webmail.com").build();


        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/users/new")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void confirmRegistration() throws Exception{
        User user = User.builder().id(1L).build();
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken("token");
        token.setExpiryDate(LocalDateTime.now().plusDays(1));
        when(tokenDaoService.getVerificationToken(anyString())).thenReturn(token);
        when(userService.createUser(any())).thenReturn(user);
        mockMvc.perform(get("/users/new/confirm")).andDo(print());

        assert(user.isEnabled());
    }

    @Test
    public void updateUser() throws Exception{
        User user = User.builder().id(1L).build();
        User updateUserData = User.builder().nickname("wert").password("123456789").email("geto@gmail.com").build();

        when(userService.updateUser(updateUserData, 1L)).thenReturn(updateUserData);
        mockMvc.perform(put("/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


    }

    @Test
    public void resetPassword() {
    }
}