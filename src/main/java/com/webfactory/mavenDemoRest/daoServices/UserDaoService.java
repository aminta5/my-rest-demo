package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;

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
