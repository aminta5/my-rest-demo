package com.webfactory.mavenDemoRest.managers;

import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;



public class AccessManagerTest {

    @Mock
    private PostService postService;
    @Mock
    private UserService userService;

    private AccessManager accessManager;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        accessManager = new AccessManager(userService, postService);
    }

    @Test
    public void authorizedUser() {
       User user = User.builder().id(1L).nickname("torro").build();
       when(userService.getUserByNickname(anyString())).thenReturn(user);
       boolean authorizedUser = accessManager.authorizedUser(user.getNickname(), user.getId());
       assertTrue(authorizedUser);
    }

    @Test
    public void notAuthorizedUser() {
        User user = User.builder().id(1L).nickname("torro").build();
        when(userService.getUserByNickname(anyString())).thenReturn(user);
        boolean authorizedUser = accessManager.authorizedUser(user.getNickname(), 2L);
        assertFalse(authorizedUser);
    }

    @Test
    public void userCanBeUpdated() {
        User user = User.builder().id(1L).nickname("hello").build();
        when(userService.getUserByNickname(anyString())).thenReturn(user);
        boolean allowedUpdate = accessManager.userCanBeUpdated(user.getNickname(), user.getId());
        assertTrue(allowedUpdate);
    }

    @Test
    public void userCannotBeUpdated() {
        User user = User.builder().id(1L).nickname("hello").build();
        when(userService.getUserByNickname(anyString())).thenReturn(user);
        boolean allowedUpdate = accessManager.userCanBeUpdated(user.getNickname(), 2L);
        assertFalse(allowedUpdate);
    }

    @Test
    public void postCanBeUpdatedOrSeen() {
        User user = User.builder().id(1L).nickname("Terry").build();
        Post post = Post.builder().id(1L).user(user).build();

        when(postService.getPostById(anyLong())).thenReturn(post);
        when(userService.getUserByNickname(anyString())).thenReturn(user);
        boolean allowedPost = accessManager.postCanBeUpdatedOrSeen(user.getNickname(), post.getId());
        assertTrue(allowedPost);
    }

    @Test
    public void postCannotBeUpdatedOrSeen() {
        User user = User.builder().id(1L).nickname("Terry").build();
        Post post = Post.builder().id(1L).user(user).build();

        when(postService.getPostById(anyLong())).thenReturn(post);
        when(userService.getUserByNickname(anyString())).thenReturn(any());
        boolean allowedPost = accessManager.postCanBeUpdatedOrSeen(user.getNickname(), 2L);
        assertFalse(allowedPost);
    }
}