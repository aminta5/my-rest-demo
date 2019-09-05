package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.helpers.Helper;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserDaoService userDaoService;


    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;

    }

    //find all users only for admins
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userDaoService.findAllUsers();
    }

    //find specific user (by id)
    @GetMapping(path = "/users/{userId}")
    public User getUser(@PathVariable String userId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        if (Helper.isAdmin(user)) {
            return userDaoService.findUserById(Long.parseLong(userId));
        } else if (!Helper.isSameUser(user, Long.parseLong(userId))) {
            throw new RuntimeException("Not authorized");
        }
        return user;
    }

    //delete user
    @DeleteMapping(path = "/users/{userId}")
    public List<User> deleteUser(@PathVariable String userId) {
        userDaoService.deleteUserById(Long.parseLong(userId));
        return userDaoService.findAllUsers();
    }


    //create user
    @PostMapping(path = "/users/create")
    public ResponseEntity<Object> createUser(@RequestBody RequestBodyUser requestBodyUser) {
        User savedUser = userDaoService.saveUser(requestBodyUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update user
    @PutMapping(path = "/users/{userId/update")
    public ResponseEntity<Object> updateUser(@RequestBody RequestBodyUser requestBodyUser, @PathVariable String userId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        User savedUser = new User();
        if (Helper.isAdmin(user) || Helper.isSameUser(user, Long.parseLong(userId))) {
            savedUser = userDaoService.updateUser(requestBodyUser, user.getId());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
