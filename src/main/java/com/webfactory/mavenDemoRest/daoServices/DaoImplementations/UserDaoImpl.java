package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserDaoImpl implements UserDaoService {

    private final UserRepository userRepository;
    //private final UserDetailsService userDetailsService;

    public UserDaoImpl(UserRepository userRepository/*, UserDetailsService userDetailsService*/) {
        this.userRepository = userRepository;
        //this.userDetailsService = userDetailsService;
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

    /*@Override
    public User findByUsernameAndPassword(String nickname, String password) {
        return userRepository.findByNicknameContainingIgnoreCaseAndPassword(nickname, password).orElseThrow(() -> new UserNotFoundException(nickname));
    }*/

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    /*@Override
    @Transactional
    public void enableRegisteredUser(User user) {
        userRepository.save(user);
    }*/

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

   /* @Override
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
    }*/
}
