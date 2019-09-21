package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.PostRepository;
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

public class PostDaoImplTest {

    private PostDaoImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostDaoImpl(postRepository, userRepository);
    }


    @Test
    public void findAllPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> postData = new ArrayList<>();
        postData.add(post1);
        postData.add(post2);

        when(postRepository.findAll()).thenReturn(postData);
        List<Post> posts = postService.findAllPosts();
        assertEquals(posts.size(), 2);
    }

    @Test
    public void findPostById() {
        Post post = Post.builder().id(1L).build();
        Optional<Post> postOptional = Optional.of(post);
        when(postRepository.findById(anyLong())).thenReturn(postOptional);
        Post foundPost = postService.findPostById(anyLong());
        assertNotNull("Post by id Not found", foundPost);
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findPostByTitle() {
        Post post = Post.builder().title("Vacation").build();
        List<Post> postData = new ArrayList<>();
        postData.add(post);
        Optional<List<Post>> postDataOptional = Optional.of(postData);
        when(postRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(postDataOptional);
        List<Post> posts = postService.findPostByTitle(anyString());
        assertEquals(posts.size(), 1);
        verify(postRepository, times(1)).findByTitleContainingIgnoreCase(anyString());
    }

    @Test
    public void savePost() {
        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        Post post = Post.builder().build();
        User foundUser = userRepository.findById(anyLong()).get();
        post.setUser(foundUser);
        when(postRepository.save(any())).thenReturn(post);
        Post savedPost = postService.savePost(post);
        assertNotNull("Post is Not saved", savedPost);
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void updatePost() {
    }

    @Test
    public void deletePostById() {
        Long postToDelete = 2L;
        postService.deletePostById(postToDelete);
        verify(postRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void findPostsByUserId() {
        Post post1 = Post.builder().build();
        Post post2 = Post.builder().build();
        List<Post> userPosts = new ArrayList<>();
        userPosts.add(post1);
        userPosts.add(post2);
        User user = User.builder().id(1L).build();
        user.setPosts(userPosts);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        List<Post> foundUserPosts = postService.findPostsByUserId(anyLong());
        assertEquals(foundUserPosts.size(), 2);
    }
}