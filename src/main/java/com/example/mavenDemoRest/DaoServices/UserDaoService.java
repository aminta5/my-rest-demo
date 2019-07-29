package com.example.mavenDemoRest.DaoServices;

import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface UserDaoService {
    List<User> findAllUsers();
    User findUserById(Long id);
    List<User> findUserByNickname(String nickname);
    Location findUserLocation(User user);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(User user);

}
