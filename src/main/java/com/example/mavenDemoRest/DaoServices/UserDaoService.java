package com.example.mavenDemoRest.DaoServices;

import com.example.mavenDemoRest.commands.UserCommand;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface UserDaoService {
    List<User> findAllUsers();
    User findUserById(Long id);
    List<User> findUserByNickname(String nickname);
    Location findUserLocation(User user);
    User saveUser(UserCommand userCommand);
    User updateUser(UserCommand userCommand, Long userId);
    void deleteUser(User user);

}
