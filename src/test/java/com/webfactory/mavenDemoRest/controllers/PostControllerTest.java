package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.exceptionHandler.CustomizedResponseEntityExceptionHandler;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest {

    @Mock
    private PostService postService;
    @Mock
    private UserService userService;
    @Mock
    private RequestBodyPostToPost requestBodyPostToPost;

    private PostController postController;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        postController = new PostController(postService, userService, requestBodyPostToPost);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).setControllerAdvice(new CustomizedResponseEntityExceptionHandler()).build();
    }

    @Test
    public void getAllPosts() throws Exception {
        List<Post> listPosts = new ArrayList<>();
        listPosts.add(Post.builder().id(1L).build());
        listPosts.add(Post.builder().id(2L).build());

        when(postService.getAllPosts()).thenReturn(listPosts);
        mockMvc.perform(get("/posts")).andExpect(status().isOk());
    }

    @Test
    public void getUsersPosts() throws Exception {
        List<Post> userPosts = new ArrayList<>();
        userPosts.add(Post.builder().id(1L).build());
        userPosts.add(Post.builder().id(2L).build());

        when(postService.getPostsByUserId(anyLong())).thenReturn(userPosts);
        mockMvc.perform(get("/users/1/posts")).andExpect(status().isOk());
    }

    @Test
    public void findPostById() throws Exception {
        Post post = Post.builder().id(1L).build();
        when(postService.getPostById(anyLong())).thenReturn(post);
        mockMvc.perform(get("/posts/1")).andExpect(status().isOk());
    }

    @Test
    public void postByIdNotFound() throws Exception {
        when(postService.getPostById(anyLong())).thenThrow(PostNotFoundException.class);
        mockMvc.perform(get("/posts/1")).andExpect(status().isNotFound());
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(delete("/posts/1")).andExpect(status().isOk());
    }
    
    @Test
    public void createPost() {
    }

    @Test
    public void updatePost() {
    }
}