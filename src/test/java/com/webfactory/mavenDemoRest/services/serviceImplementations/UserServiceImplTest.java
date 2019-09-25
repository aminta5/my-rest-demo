package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void findAllUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> userData = new ArrayList<>();
        userData.add(user1);
        userData.add(user2);

        when(userRepository.findAll()).thenReturn(userData);
        List<User> users = userService.getAllUsers();
        assertEquals(users.size(), 2);
    }

    @Test
    public void findUserById() {
        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        User foundUser = userService.getUserById(1L);
        assertNotNull("User not found", foundUser);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, never()).findAll();
    }

    @Test(expected = UserNotFoundException.class)
    public void throwExceptionIfUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        userService.getUserById(anyLong());
    }

    @Test
    public void findUserByNickname() {
        User user = User.builder().nickname("miki").build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findByNicknameContainingIgnoreCase(anyString())).thenReturn(userOptional);
        User foundUserByNickname = userService.getUserByNickname(anyString());
        assertNotNull("User by nickname not found", foundUserByNickname);
        verify(userRepository, times(1)).findByNicknameContainingIgnoreCase(anyString());
    }

    @Test(expected = UserNotFoundException.class)
    public void throwExceptionIfUserNotFoundByNickname() {
        when(userRepository.findByNicknameContainingIgnoreCase(anyString())).thenReturn(Optional.empty());
        userService.getUserByNickname(anyString());
    }


    @Test
    public void saveUser() {
        User user = User.builder().build();
        when(userRepository.save(any())).thenReturn(user);
        User savedUser = userService.createUser(any());
        assertNotNull("User is not saved", savedUser);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateUser() {
        User userToUpdate = User.builder().id(1L).build();
        Optional<User> userToUpdateOptional = Optional.of(userToUpdate);
        User userUpdateData = User.builder().firstName("xavier").lastName("wolf").build();
        when(userRepository.findById(anyLong())).thenReturn(userToUpdateOptional);
        when(userRepository.save(any())).thenReturn(userUpdateData);
        User savedUpdatedUser = userService.updateUser(userUpdateData, userToUpdate.getId());
        assertNotNull(savedUpdatedUser);
        verify(userRepository, times(1)).save(any());
    }

    @Test(expected = UserNotFoundException.class)
    public void userToUpdateNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        User user = User.builder().build();
        userService.updateUser(user, anyLong());
    }

    @Test
    public void locationCreatedIfNull(){
        User userToUpdate = User.builder().nickname("gogo").build();
        Optional<User> userOptional = Optional.of(userToUpdate);
        User userUpdateData = User.builder().build();
        when(userRepository.save(any())).thenReturn(userToUpdate);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        User updatedUser = userService.updateUser(userUpdateData, anyLong());
        assertNotNull(updatedUser.getLocation());
    }

    @Test
    public void deleteUserById() {
        Long idToDelete = 2L;
        userService.deleteUserById(idToDelete);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findUserByEmail() {
        User user = User.builder().build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findByEmail(anyString())).thenReturn(userOptional);
        User userByEmail = userService.getUserByEmail(anyString());
        assertNotNull("User by e mail ot found", userByEmail);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}