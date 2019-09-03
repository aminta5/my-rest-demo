package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
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
    public List<User> getUsers(){
        return userDaoService.findAllUsers();
    }

    //find specific user (by id)
    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable String id, Authentication authentication){
        User user = userDaoService.findUserByNickname(authentication.getName());
        if(user.getUserType().equals(UserType.ROLE_ADMIN)){
            return userDaoService.findUserById(Long.parseLong(id));
        }
        else if(user.getId() != Long.parseLong(id)){
            throw new RuntimeException("Not authorized");
        }
        return user;
    }

    //delete user
    @DeleteMapping(path = "/users/{id}/delete")
    public List<User> deleteUser(@PathVariable String id){
        userDaoService.deleteUserById(Long.parseLong(id));
        return userDaoService.findAllUsers();
    }


    //create user
    @PostMapping(path = "/users/create")
    public ResponseEntity<Object> createUser(@RequestBody RequestBodyUser requestBodyUser){
        User savedUser = userDaoService.saveUser(requestBodyUser);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update user
    @PutMapping(path = "/users/update")
    public ResponseEntity<Object> updateUser(@RequestBody RequestBodyUser requestBodyUser, Authentication authentication){
        User user = userDaoService.findUserByNickname(authentication.getName());
        User savedUser = userDaoService.updateUser(requestBodyUser, user.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
