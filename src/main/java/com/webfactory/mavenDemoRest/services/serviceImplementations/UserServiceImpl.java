package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public UserServiceImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    private Logger logger = Logger.getLogger(getClass().getName());


    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).orElse(null);

    }

    @Cacheable(value = "usersIds", key = "#id")
    @Override
    public User getUserById(Long id) {
        logger.info("getUserById invoked");
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Cacheable(value = "usersNickname", key = "#nickname")
    @Override
    public User getUserByNickname(String nickname) {
        logger.info("getUserByNickname invoked");
        return userRepository.findByNicknameContainingIgnoreCase(nickname).orElseThrow(() -> new UserNotFoundException(nickname));
    }

    @Override
    @Caching(put = {
            @CachePut(value = "usersIds", key = "#result.id"),
            @CachePut(value = "usersNickname", key = "#result.nickname"),
            @CachePut(value = "usersEmails", key = "#result.email")
    })
    public User createUser(User user) {
        Optional<Location> locationOptional = locationRepository.findByCityContainingIgnoreCase(user.getLocation().getCity());
        if(locationOptional.isPresent()){
            Location location = locationOptional.get();
            location.addUser(user);
            user.setLocation(location);
            //locationRepository.save(location);
        }
        return userRepository.save(user);
    }

    @Override
    @Caching(put = {
            @CachePut(value = "usersIds", key = "#userId"),
            @CachePut(value = "userNickname", key = "#result.nickname"),
            @CachePut(value = "usersEmails", key = "#result.email")
    })
    public User updateUser(User updatedUserObject, Long userId) {
        logger.info("updateUser invoked");
        Optional<User> userToUpdateOptional = userRepository.findById(userId);

        User userToUpdate = userToUpdateOptional.orElseThrow(() -> new UserNotFoundException(userId.toString()));

        if(updatedUserObject.getLocation() != null){
            Optional<Location> locationExists = locationRepository.findByCityContainingIgnoreCase(updatedUserObject.getLocation().getCity());
            Location location;
            if(locationExists.isPresent()){
                location = locationExists.get();
            }else{
                location = updatedUserObject.getLocation();
            }

            location.addUser(userToUpdate);
            userToUpdate.setLocation(location);

        }
        /*if(updatedUserObject.getLocation() != null && updatedUserObject.getLocation() != userToUpdate.getLocation()){
            userToUpdate.setLocation(updatedUserObject.getLocation());
        }*/

        //Location locationToUpdate = userToUpdate.getLocation();
        /*if (locationToUpdate == null) {
            locationToUpdate = new Location();
        }
        Location newLocation = updatedUserObject.getLocation();*/

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
        /*if (newLocation != null) {
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
        }*/

       //userToUpdate.setLocation(locationToUpdate);

        return userRepository.save(userToUpdate);
    }

    @Caching(evict = {
            @CacheEvict(value = "usersIds", key = "#id"),
            @CacheEvict(value = "postsByUserIds", key = "#id")
    })
    @Override
    public User deleteUserById(Long id) {
        logger.info("deleteUserById invoked");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.deleteById(id);
        return user;
    }

    @Cacheable(value = "usersEmails", key = "#email")
    @Override
    public User getUserByEmail(String email) {
        logger.info("getUserByEmail invoked");
        return userRepository.findByEmail(email).orElse(null);
    }

}
