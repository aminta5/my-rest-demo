package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserDaoService/* extends UserDetailsService*/ {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByNickname(String nickname);

    User saveUser(User user);

    User updateUser(User user, Long userId);

    /*User findByUsernameAndPassword(String username, String password);*/

    void deleteUserById(Long id);

    /*void enableRegisteredUser(User user);*/

    User findUserByEmail(String email);

}
