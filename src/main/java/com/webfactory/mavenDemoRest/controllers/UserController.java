package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//@EnableResourceServer
@RestController
public class UserController {

    private final UserDaoService userDaoService;


    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;

    }

    //find all users
    @GetMapping(path = "/users")
    public List<User> getUsers(){
        return userDaoService.findAllUsers();
    }

    //find specific user (by id)
    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable String id){
        long longId = Long.parseLong(id);
        Optional<User> optionalUser = userDaoService.findAllUsers().stream().filter(u -> u.getId() == longId).findFirst();
        return optionalUser.orElse(null);
    }

    //delete user
    @DeleteMapping(path = "/users/{id}/delete")
    public List<User> deleteUser(@PathVariable String id){
        Optional<User> optUser = userDaoService.findAllUsers().stream().filter(u -> u.getId() == Long.parseLong(id)).findFirst();
        if(optUser.isPresent()){
            User u = optUser.get();
            userDaoService.deleteUser(u);
        }
        else{
            throw new RuntimeException("User not found");
        }
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
    @PutMapping(path = "/users/update/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody RequestBodyUser requestBodyUser){

        User savedUser = userDaoService.updateUser(requestBodyUser, Long.parseLong(userId));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
