package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.exceptions.TokenNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import com.webfactory.mavenDemoRest.repositories.VerificationTokenRepository;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyUser;
import com.webfactory.mavenDemoRest.converters.RequestBodyLocationToLocation;
import com.webfactory.mavenDemoRest.converters.RequestBodyUserToUser;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDaoImpl implements UserDaoService {

    private final UserRepository userRepository;
    private final RequestBodyLocationToLocation requestBodyLocationToLocation;
    private final RequestBodyUserToUser requestBodyUserToUser;
    private final VerificationTokenRepository tokenRepository;

    public UserDaoImpl(UserRepository userRepository, RequestBodyLocationToLocation requestBodyLocationToLocation, RequestBodyUserToUser requestBodyUserToUser, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
        this.requestBodyUserToUser = requestBodyUserToUser;
        this.tokenRepository = tokenRepository;
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
    public User saveUser(RequestBodyUser requestBodyUser) {
        return userRepository.save(requestBodyUserToUser.convert(requestBodyUser));
    }

    @Override
    public User updateUser(RequestBodyUser requestBodyUser, Long userId) {
        Optional<User> userToUpdateOptional = userRepository.findById(userId);
        User userToUpdate = userToUpdateOptional.orElseThrow(() -> new UserNotFoundException(userId.toString()));
        Location locationToUpdate = userToUpdate.getLocation();
        if (locationToUpdate == null) {
            locationToUpdate = new Location();
        }
        Location newLocation = requestBodyLocationToLocation.convert(requestBodyUser.getLocation());

        //updating the user
        if (requestBodyUser.getFirstName() != null) {
            userToUpdate.setFirstName(requestBodyUser.getFirstName());
        }
        if (requestBodyUser.getLastName() != null) {
            userToUpdate.setLastName(requestBodyUser.getLastName());
        }
        if (requestBodyUser.getEmail() != null) {
            userToUpdate.setEmail(requestBodyUser.getEmail());
        }
        if (requestBodyUser.getPassword() != null) {
            userToUpdate.setPassword(requestBodyUser.getPassword());
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
    public User findByUsernameAndPassword(String nickname, String password) {
        return userRepository.findByNicknameContainingIgnoreCaseAndPassword(nickname, password).orElseThrow(() -> new UserNotFoundException(nickname));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void enableRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNicknameContainingIgnoreCase(username).orElseThrow(() -> new UserNotFoundException(username));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        try {
            if (!user.isEnabled()) {
                throw new UsernameNotFoundException("Please enable your account.");
            }
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }

        return new org.springframework.security.core.userdetails.User(user.getNickname(), user.getPassword(),
                user.isEnabled(), true, true, true, mapRolesToAuthorities(user.getUserType()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserType role) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }
}
