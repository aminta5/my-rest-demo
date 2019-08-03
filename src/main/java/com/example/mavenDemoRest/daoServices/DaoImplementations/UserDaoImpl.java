package com.example.mavenDemoRest.daoServices.DaoImplementations;

import com.example.mavenDemoRest.daoServices.UserDaoService;
import com.example.mavenDemoRest.requestBodies.RequestBodyUser;
import com.example.mavenDemoRest.converters.RequestBodyLocationToLocation;
import com.example.mavenDemoRest.converters.RequestBodyUserToUser;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDaoImpl implements UserDaoService{

    private final UserRepository userRepository;
    private final RequestBodyLocationToLocation requestBodyLocationToLocation;
    private final RequestBodyUserToUser requestBodyUserToUser;

    public UserDaoImpl(UserRepository userRepository, RequestBodyLocationToLocation requestBodyLocationToLocation, RequestBodyUserToUser requestBodyUserToUser) {
        this.userRepository = userRepository;
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
        this.requestBodyUserToUser = requestBodyUserToUser;
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
        findAllUsers().stream().filter(u -> u.getNickname().equalsIgnoreCase(nickname)).forEach(users :: add);
        return users;
    }


    @Override
    public Location findUserLocation(User user){
        return user.getLocation();
    }

    @Override
    public User saveUser(RequestBodyUser requestBodyUser){
        User savedUser = userRepository.save(requestBodyUserToUser.convert(requestBodyUser));
        return savedUser;
    }


    @Override
    public User updateUser(RequestBodyUser requestBodyUser, Long userId){
        Optional<User> userToUpdateOptional = userRepository.findById(userId);
        User userToUpdate = userToUpdateOptional.orElseThrow(() -> new RuntimeException("User is Not found"));
        Location locationToUpdate = userToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = requestBodyLocationToLocation.convert(requestBodyUser.getLocation());

        //updating the user
        if(requestBodyUser.getFirstName() != null){
            userToUpdate.setFirstName(requestBodyUser.getFirstName());
        }
        if(requestBodyUser.getLastName() != null){
            userToUpdate.setLastName(requestBodyUser.getLastName());
        }
        if(requestBodyUser.getNickname() != null){
            userToUpdate.setNickname(requestBodyUser.getNickname());
        }
        if(requestBodyUser.getEmail() != null){
            userToUpdate.setEmail(requestBodyUser.getEmail());
        }
        if(requestBodyUser.getPassword() != null){
            userToUpdate.setPassword(requestBodyUser.getPassword());
        }

        //updating the location
        if(newLocation != null){
            if(newLocation.getCity() != null){
                locationToUpdate.setCity(newLocation.getCity());
            }
            if(newLocation.getCountry() != null){
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if(newLocation.getLongitude() != null && newLocation.getLongitude() != 0.0){
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if(newLocation.getLatitude() != null && newLocation.getLatitude() != 0.0){
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }

        userToUpdate.setLocation(locationToUpdate);

        User savedUser = userRepository.save(userToUpdate);
        return savedUser;
    }

    @Override
    public void deleteUser(User user){
        userRepository.delete(user);
    }

}
