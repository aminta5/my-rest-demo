package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyUserToUser;
import com.webfactory.mavenDemoRest.daoServices.VerificationTokenDaoService;
import com.webfactory.mavenDemoRest.events.OnRegistrationSuccessEvent;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.exceptions.ExpiredTokenException;
import com.webfactory.mavenDemoRest.exceptions.InvalidTokenException;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.util.GenericResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
public class UserController {

    private final UserDaoService userDaoService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenDaoService tokenDaoService;
    private final MessageSource messages;
    private final RequestBodyUserToUser requestBodyUserToUser;
    private final MailSender mailSender;


    private Logger logger = Logger.getLogger(getClass().getName());

    public UserController(UserDaoService userDaoService, ApplicationEventPublisher eventPublisher, VerificationTokenDaoService tokenDaoService, @Qualifier("messageSource") MessageSource messages, RequestBodyUserToUser requestBodyUserToUser, MailSender mailSender) {
        this.userDaoService = userDaoService;

        this.eventPublisher = eventPublisher;
        this.tokenDaoService = tokenDaoService;
        this.messages = messages;
        this.requestBodyUserToUser = requestBodyUserToUser;
        this.mailSender = mailSender;
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
    public List<User> deleteUser(@PathVariable Long userId) {
        userDaoService.deleteUserById(userId);
        return userDaoService.findAllUsers();
    }


    //create user
    @PostMapping(path = "/users/new")
    public ResponseEntity<User> createUser(@Valid @RequestBody RequestBodyUser requestBodyUser) {
        User savedUser = userDaoService.saveUser(requestBodyUserToUser.convert(requestBodyUser));
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
        userDaoService.saveUser(user);
        return null;
    }

    //update user
    //@Transactional
    @PutMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.userCanBeUpdated(authentication, #userId)")
    public ResponseEntity<User> updateUser(@Valid @RequestBody RequestBodyUser requestBodyUser, @P("userId") @PathVariable Long userId) {
        User savedUser = userDaoService.updateUser(requestBodyUserToUser.convert(requestBodyUser), userId);
        return new ResponseEntity<>(savedUser, HttpStatus.ACCEPTED);
    }

    // Reset password
    @RequestMapping(value = "/users/reset/password", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(@RequestBody RequestBodyUser requestBodyUser) {
        final User user = userDaoService.findUserByEmail(requestBodyUser.getEmail());
        SimpleMailMessage email = new SimpleMailMessage();
        String message = messages.getMessage("message.passwordChange", null, Locale.getDefault());
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userDaoService.createPasswordResetTokenForUser(user, token);
            String url = "'http://localhost:8080/users/password/change?id=" + user.getId() + "&token" + token +"\'";
            email.setTo(user.getEmail());
            email.setSubject("Password change");
            email.setText(message + url);
            logger.info("Confirmation link:" + url);
            mailSender.send(email);
        }
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, Locale.getDefault()));
    }
}
