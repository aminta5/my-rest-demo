package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyUserToUser;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.services.VerificationTokenService;
import com.webfactory.mavenDemoRest.events.OnRegistrationSuccessEvent;
import com.webfactory.mavenDemoRest.exceptions.ExpiredTokenException;
import com.webfactory.mavenDemoRest.exceptions.InvalidTokenException;
import com.webfactory.mavenDemoRest.model.PasswordChange;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestController
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService tokenDaoService;
    private final MessageSource messages;
    private final RequestBodyUserToUser requestBodyUserToUser;

    public UserController(UserService userService,
                          ApplicationEventPublisher eventPublisher,
                          VerificationTokenService tokenDaoService,
                          @Qualifier("messageSource") MessageSource messages,
                          RequestBodyUserToUser requestBodyUserToUser) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.tokenDaoService = tokenDaoService;
        this.messages = messages;
        this.requestBodyUserToUser = requestBodyUserToUser;
    }

    //find all users only for admins
    @GetMapping(path = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //find specific user (by id)
    @GetMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.authorizedUser(authentication, #userId)")
    public User getUser(@P("userId") @PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    //delete user
    @DeleteMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return userService.getAllUsers();
    }

    //create user
    @PostMapping(path = "/users/new")
    public ResponseEntity<User> createUser(@Valid @RequestBody RequestBodyUser requestBodyUser) {
        User savedUser = userService.createUser(requestBodyUserToUser.convert(requestBodyUser));
        eventPublisher.publishEvent(new OnRegistrationSuccessEvent(savedUser));
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //confirm user
    @GetMapping("/users/new/confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenDaoService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, Locale.getDefault());
            throw new InvalidTokenException(message);
        }
        User user = verificationToken.getUser();
        if ((verificationToken.getExpiryDate().isBefore(LocalDateTime.now()))) {
            String message = messages.getMessage("auth.message.expired", null, Locale.getDefault());
            throw new ExpiredTokenException(message);
        }

        user.setEnabled(true);
        userService.createUser(user);
        return null;
    }

    //update user
    @PutMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.userCanBeUpdated(authentication, #userId)")
    public ResponseEntity<User> updateUser(@Valid @RequestBody RequestBodyUser requestBodyUser, @P("userId") @PathVariable Long userId) {
        User savedUser = userService.updateUser(requestBodyUserToUser.convert(requestBodyUser), userId);
        return new ResponseEntity<>(savedUser, HttpStatus.ACCEPTED);
    }

    // Reset password
    @RequestMapping(value = "/users/{userId}/reset/password", method = RequestMethod.POST)
    @PreAuthorize("@accessManager.userCanBeUpdated(authentication, #userId)")
    public boolean resetPassword(@Valid @RequestBody PasswordChange passwordChange, @PathVariable Long userId) {
        User user = userService.getUserByEmail(passwordChange.getEmail());
        if (user != null && user.getId().equals(userId)) {
            user.setPassword(passwordChange.getPassword());
            userService.createUser(user);
            return true;
        }
        return false;
    }
}
