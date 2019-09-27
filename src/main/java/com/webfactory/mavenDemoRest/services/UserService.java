package com.webfactory.mavenDemoRest.services;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long id);

    User getUserByNickname(String nickname);

    User createUser(User user);

    User updateUser(User user, Long userId);

    void deleteUserById(Long id);

    User getUserByEmail(String email);
}
