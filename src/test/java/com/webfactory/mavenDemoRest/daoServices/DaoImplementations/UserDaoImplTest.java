package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

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

public class UserDaoImplTest {

    private UserDaoImpl userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserDaoImpl(userRepository);
    }

    @Test
    public void findAllUsersTest() {
        User user1 = new User();
        User user2 = new User();
        List<User> userData = new ArrayList<>();
        userData.add(user1);
        userData.add(user2);

        when(userRepository.findAll()).thenReturn(userData);
        List<User> users = userService.findAllUsers();
        assertEquals(users.size(), 2);
    }

    @Test
    public void findUserByIdTest() {
        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        User foundUser = userService.findUserById(1L);
        assertNotNull("User not found", foundUser);
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, never()).findAll();
    }

    @Test
    public void findUserByNickname() {
        User user = User.builder().nickname("miki").build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findByNicknameContainingIgnoreCase(anyString())).thenReturn(userOptional);
        User foundUserByNickname = userService.findUserByNickname(anyString());
        assertNotNull("User by nickname not found", foundUserByNickname);
        verify(userRepository, times(1)).findByNicknameContainingIgnoreCase(anyString());
    }

    @Test
    public void saveUser() {
        User user = User.builder().build();
        when(userRepository.save(any())).thenReturn(user);
        User savedUser = userService.saveUser(any());
        assertNotNull("User is not saved", savedUser);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void updateUser() {
        User userToUpdate = User.builder().build();
        Optional<User> userToUpdateOptional = Optional.of(userToUpdate);
        when(userRepository.findById(anyLong())).thenReturn(userToUpdateOptional);
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
        User userByEmail = userService.findUserByEmail(anyString());
        assertNotNull("User by e mail ot found", userByEmail);
        verify(userRepository, times(1)).findByEmail(anyString());
    }
}