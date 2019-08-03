package com.example.mavenDemoRest.daoServices;

import com.example.mavenDemoRest.requestBodies.RequestBodyUser;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface UserDaoService {
    List<User> findAllUsers();
    User findUserById(Long id);
    List<User> findUserByNickname(String nickname);
    Location findUserLocation(User user);
    User saveUser(RequestBodyUser requestBodyUser);
    User updateUser(RequestBodyUser requestBodyUser, Long userId);
    void deleteUser(User user);

}
