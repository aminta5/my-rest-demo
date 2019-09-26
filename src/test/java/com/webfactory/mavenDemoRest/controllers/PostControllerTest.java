package com.webfactory.mavenDemoRest.controllers;

import org.codehaus.jackson.map.ObjectMapper;
import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.exceptionHandler.CustomizedResponseEntityExceptionHandler;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @WithMockUser
    public void createPost() throws Exception{
        User user = User.builder().id(1L).build();
        Post post = Post.builder().title("some post").description("whats up?").user(user).build();

        when(userService.getUserByNickname(anyString())).thenReturn(user);
        when(postService.createPost(any())).thenReturn(post);
        //String token = obtainAccessToken(user.getNickname(), user.getPassword());
        mockMvc.perform(post("/posts/new")
                //.header("Authorization", "Bearer " + token)
                .content(new ObjectMapper().writeValueAsString(post))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "filip-client");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("filip-client","filip-secret"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void updatePost() {
    }
}