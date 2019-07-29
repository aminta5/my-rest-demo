package com.example.mavenDemoRest.DaoServices.DaoImplementations;

import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserDaoService{

    UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users ::  add);
        return users;

    }

    @Override
    public User findUserById(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public List<User> findUserByNickname(String nickname){
        List<User> users = new ArrayList<>();
        findAllUsers().stream().filter(u -> u.getNickname() == nickname).forEach(users :: add);
        return users;
    }


    @Override
    public Location findUserLocation(User user){
        //User user = findUserById(userId);
        return user.getLocation();
    }

    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    //TODO
    @Override
    public User updateUser(User user){
        return null;
    }

    @Override
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
