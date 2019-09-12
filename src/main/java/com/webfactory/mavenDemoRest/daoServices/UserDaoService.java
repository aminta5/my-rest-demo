package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserDaoService extends UserDetailsService {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByNickname(String nickname);

    User saveUser(RequestBodyUser requestBodyUser);

    User updateUser(RequestBodyUser requestBodyUser, Long userId);

    public User findByUsernameAndPassword(String username, String password);

    void deleteUserById(Long id);

    public void enableRegisteredUser(User user);

    User findUserByEmail(String email);

}
