package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;

import java.util.List;

public interface UserDaoService {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByNickname(String nickname);

    User saveUser(RequestBodyUser requestBodyUser);

    User updateUser(RequestBodyUser requestBodyUser, Long userId);

    void deleteUserById(Long id);

}
