package com.webfactory.mavenDemoRest.services;

import com.webfactory.mavenDemoRest.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByNickname(String nickname);

    User createUser(User user);

    User updateUser(User user, Long userId);

    void deleteUserById(Long id);

    User getUserByEmail(String email);
}
