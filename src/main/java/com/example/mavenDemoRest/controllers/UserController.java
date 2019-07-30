package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.DataStore;
import com.example.mavenDemoRest.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/users")
    public List<User> getUsers(){
        return userDaoService.findAllUsers();
    }

    @GetMapping(path = "/users/{id}")
    public User getUser(@PathVariable String id){
        long longId = Long.parseLong(id);
        //return DataStore.getUsers().stream().filter(u -> u.getId() == longId).findFirst().get();
        Optional<User> optionalUser = userDaoService.findAllUsers().stream().filter(u -> u.getId() == longId).findFirst();
        return optionalUser.orElse(null);
    }

    @GetMapping(path = "/users/{id}/delete")
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

    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = userDaoService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //TODO
    @PutMapping(path = "/users/{id}/update")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody User user){
        User savedUser = userDaoService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
