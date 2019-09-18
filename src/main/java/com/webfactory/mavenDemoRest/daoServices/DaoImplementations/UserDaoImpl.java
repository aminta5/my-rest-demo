package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDaoImpl implements UserDaoService {

    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;

    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Override
    public User findUserByNickname(String nickname) {
        return userRepository.findByNicknameContainingIgnoreCase(nickname).orElseThrow(() -> new UserNotFoundException(nickname));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User updatedUserObject, Long userId) {
        Optional<User> userToUpdateOptional = userRepository.findById(userId);
        User userToUpdate = userToUpdateOptional.orElseThrow(() -> new UserNotFoundException(userId.toString()));
        Location locationToUpdate = userToUpdate.getLocation();
        if (locationToUpdate == null) {
            locationToUpdate = new Location();
        }
        Location newLocation = updatedUserObject.getLocation();

        //updating the user
        if (updatedUserObject.getFirstName() != null) {
            userToUpdate.setFirstName(updatedUserObject.getFirstName());
        }
        if (updatedUserObject.getLastName() != null) {
            userToUpdate.setLastName(updatedUserObject.getLastName());
        }
        if (updatedUserObject.getEmail() != null) {
            userToUpdate.setEmail(updatedUserObject.getEmail());
        }
        if (updatedUserObject.getPassword() != null) {
            userToUpdate.setPassword(updatedUserObject.getPassword());
        }

        //updating the location
        if (newLocation != null) {
            if (newLocation.getCity() != null) {
                locationToUpdate.setCity(newLocation.getCity());
            }
            if (newLocation.getCountry() != null) {
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if (newLocation.getLongitude() != null && newLocation.getLongitude() != 0.0) {
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if (newLocation.getLatitude() != null && newLocation.getLatitude() != 0.0) {
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }

        userToUpdate.setLocation(locationToUpdate);

        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
