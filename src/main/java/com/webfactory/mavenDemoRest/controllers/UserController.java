package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.events.OnRegistrationSuccessEvent;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.exceptions.ExpiredTokenException;
import com.webfactory.mavenDemoRest.exceptions.InvalidTokenException;
import com.webfactory.mavenDemoRest.exceptions.NotValidUserException;
import com.webfactory.mavenDemoRest.exceptions.UserAlreadyExistsException;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@RestController
public class UserController {

    private final UserDaoService userDaoService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenDaoService tokenDaoService;
    private final MessageSource messages;

    private Logger logger = Logger.getLogger(getClass().getName());


    public UserController(UserDaoService userDaoService, ApplicationEventPublisher eventPublisher, VerificationTokenDaoService tokenDaoService, @Qualifier("messageSource") MessageSource messages) {
        this.userDaoService = userDaoService;

        this.eventPublisher = eventPublisher;
        this.tokenDaoService = tokenDaoService;
        this.messages = messages;
    }

    //find all users only for admins
    @GetMapping(path = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<User> getAllUsers() {
        return userDaoService.findAllUsers();
    }

    //find specific user (by id)
    @GetMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.authorizedUser(authentication, #userId)")
    public User getUser(@P("userId") @PathVariable Long userId) {
        return userDaoService.findUserById(userId);
    }

    //delete user
    @DeleteMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> deleteUser(@PathVariable String userId) {
        userDaoService.deleteUserById(Long.parseLong(userId));
        return userDaoService.findAllUsers();
    }


    //create user
    @PostMapping(path = "/users/new")
    public ResponseEntity<Object> createUser(@RequestBody RequestBodyUser requestBodyUser, BindingResult bindingResult, WebRequest request) {
        String nickname = requestBodyUser.getNickname();
        User registeredUser = userDaoService.findUserByNickname(nickname);

        if(bindingResult.hasErrors()){
            throw new NotValidUserException();
        }

        if(registeredUser != null){
            throw new UserAlreadyExistsException();
        }
        User savedUser = userDaoService.saveUser(requestBodyUser);

        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationSuccessEvent(savedUser, request.getLocale(), appUrl));
        }catch(Exception e) {
            e.printStackTrace();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //confirm user
    @GetMapping("/users/new/confirm")
    public String confirmRegistration(WebRequest request ,@RequestParam("token") String token) {
        Locale locale =request.getLocale();
        VerificationToken verificationToken = tokenDaoService.getVerificationToken(token);
        if(verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            throw new InvalidTokenException(message);
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime()-calendar.getTime().getTime())<=0) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            throw new ExpiredTokenException(message);
        }

        user.setEnabled(true);
        userDaoService.enableRegisteredUser(user);
        return null;
    }

    //update user
    @PutMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.userCanBeUpdated(authentication, #userId)")
    public ResponseEntity<Object> updateUser(@RequestBody RequestBodyUser requestBodyUser, @P("userId") @PathVariable Long userId) {
        User savedUser = userDaoService.updateUser(requestBodyUser, userId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
