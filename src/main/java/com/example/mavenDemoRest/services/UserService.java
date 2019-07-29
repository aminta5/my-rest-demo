package com.example.mavenDemoRest.services;

import com.example.mavenDemoRest.constants.UserType;
import com.example.mavenDemoRest.controllers.UserController;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private static UserService instance = new UserService();
    //private static UserController uController = new UserController();

    //private constructor because this is singleton pattern
    private UserService() {}

    public static UserService getInstance() {
        return instance;
    }

    //da probam so builder pattern istiov metod, ili da zamenam so arg constructor
    public User createUser(long id, String firstName, String lastName, String nickname, String email, String password, Location location, UserType userType) {
        User user = new User();
        //user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPassword(password);
        user.setLocation(location);
        user.setUserType(userType);

        return user;
    }

    //public static List<User> getUsers(){
        //return uController.getUsers();
    //}
}
