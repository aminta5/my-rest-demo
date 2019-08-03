package com.example.mavenDemoRest.DaoServices.DaoImplementations;

import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.commands.UserCommand;
import com.example.mavenDemoRest.converters.LocationCommandToLocation;
import com.example.mavenDemoRest.converters.UserCommandToUser;
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
    private final LocationCommandToLocation locationCommandToLocation;
    private final UserCommandToUser userCommandToUser;

    public UserDaoImpl(UserRepository userRepository, LocationCommandToLocation locationCommandToLocation, UserCommandToUser userCommandToUser) {
        this.userRepository = userRepository;
        this.locationCommandToLocation = locationCommandToLocation;
        this.userCommandToUser = userCommandToUser;
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
    public User saveUser(UserCommand userCommand){
        User savedUser = userRepository.save(userCommandToUser.convert(userCommand));
        return savedUser;
    }


    @Override
    public User updateUser(UserCommand userCommand, Long userId){
        Optional<User> userToUpdateOptional = userRepository.findById(userId);
        User userToUpdate = userToUpdateOptional.orElseThrow(() -> new RuntimeException("User is Not found"));
        Location locationToUpdate = userToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = locationCommandToLocation.convert(userCommand.getLocation());

        //updating the user
        if(userCommand.getFirstName() != null){
            userToUpdate.setFirstName(userCommand.getFirstName());
        }
        if(userCommand.getLastName() != null){
            userToUpdate.setLastName(userCommand.getLastName());
        }
        if(userCommand.getNickname() != null){
            userToUpdate.setNickname(userCommand.getNickname());
        }
        if(userCommand.getEmail() != null){
            userToUpdate.setEmail(userCommand.getEmail());
        }
        if(userCommand.getPassword() != null){
            userToUpdate.setPassword(userCommand.getPassword());
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
