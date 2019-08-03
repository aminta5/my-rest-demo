package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.commands.UserCommand;
import com.example.mavenDemoRest.converters.LocationCommandToLocation;
import com.example.mavenDemoRest.converters.UserCommandToUser;
import com.example.mavenDemoRest.model.Location;
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
    private final UserCommandToUser userCommandToUser;
    private final LocationCommandToLocation locationCommandToLocation;

    public UserController(UserDaoService userDaoService, UserCommandToUser userCommandToUser, LocationCommandToLocation locationCommandToLocation) {
        this.userDaoService = userDaoService;
        this.userCommandToUser = userCommandToUser;
        this.locationCommandToLocation = locationCommandToLocation;
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
        //return DataStore.getUsers().stream().filter(u -> u.getId() == longId).findFirst().get();
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
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@RequestBody UserCommand userCommand){
        User savedUser = userDaoService.createUser(userCommandToUser.convert(userCommand));
        //User savedUser = userDaoService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update user
    @PutMapping(path = "/users/update/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable String userId, @RequestBody UserCommand userCommand){
        User userToUpdate = userDaoService.findUserById(Long.parseLong(userId));
        Location locationToUpdate = userToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = locationCommandToLocation.convert(userCommand.getLocation());

        //updating the user
        if(userCommand.getFirstName() != null){
            userToUpdate.setFirstName(userCommand.getFirstName());
        }
        if(userCommand.getLastName() != null){
            userToUpdate.setLastName(userCommand.getLastName());
        }
        if(userCommand.getNickname() != null){
            userToUpdate.setNickname(userCommand.getNickname());
        }
        if(userCommand.getEmail() != null){
            userToUpdate.setEmail(userCommand.getEmail());
        }
        if(userCommand.getPassword() != null){
            userToUpdate.setPassword(userCommand.getPassword());
        }

        //updating the location
        if(newLocation != null){
            if(newLocation.getCity() != null){
                locationToUpdate.setCity(newLocation.getCity());
            }
            if(newLocation.getCountry() != null){
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if(newLocation.getLongitude() != null && newLocation.getLongitude() != 0.0){
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if(newLocation.getLatitude() != null && newLocation.getLatitude() != 0.0){
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }

        userToUpdate.setLocation(locationToUpdate);

        User savedUser = userDaoService.createUser(userToUpdate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
