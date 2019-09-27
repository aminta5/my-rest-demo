package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        //List<User> users = new ArrayList<>();
        return userRepository.findAll(pageable).orElse(null);/*.forEach(users::add)*/
        //return users;

    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Override
    public User getUserByNickname(String nickname) {
        return userRepository.findByNicknameContainingIgnoreCase(nickname).orElseThrow(() -> new UserNotFoundException(nickname));
    }

    @Override
    public User createUser(User user) {
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
            if (newLocation.getLongitude() != null) {
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if (newLocation.getLatitude() != null) {
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
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}
