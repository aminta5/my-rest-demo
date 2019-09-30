package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.PostRepository;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    private PostServiceImpl postService;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postService = new PostServiceImpl(postRepository, userRepository);
    }


    @Test
    public void findAllPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> postData = new ArrayList<>();
        postData.add(post1);
        postData.add(post2);
        Pageable pageable = PageRequest.of(0,1);
        Page<Post> postsPage = new PageImpl<>(postData);
        Optional<Page<Post>> postsPageOptional = Optional.of(postsPage);

        when(postRepository.findAll(any(Pageable.class))).thenReturn(postsPageOptional);
        Page<Post> returnedPostsPage = postService.getAllPosts(pageable);
        assertThat(returnedPostsPage.getContent(), hasSize(2));
        assertThat(returnedPostsPage.isFirst(), is(true));
        assertThat(returnedPostsPage.isLast(), is(true));
        assertThat(returnedPostsPage.hasNext(), is(false));
    }

    @Test
    public void findPostById() {
        Post post = Post.builder().id(1L).build();
        Optional<Post> postOptional = Optional.of(post);
        when(postRepository.findById(anyLong())).thenReturn(postOptional);
        Post foundPost = postService.getPostById(anyLong());
        assertNotNull("Post by id Not found", foundPost);
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test(expected = PostNotFoundException.class)
    public void throwExceptionIfPostNotFound(){
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());
        postService.getPostById(anyLong());
    }

    @Test
    public void findPostByTitle() {
        Post post = Post.builder().title("Vacation").build();
        List<Post> postData = new ArrayList<>();
        postData.add(post);
        Optional<List<Post>> postDataOptional = Optional.of(postData);
       // when(postRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(postDataOptional);
       // List<Post> posts = postService.getPostByTitle(anyString());
       // assertEquals(posts.size(), 1);
      //  verify(postRepository, times(1)).findByTitleContainingIgnoreCase(anyString());
    }

    @Test(expected = PostNotFoundException.class)
    public void throwExceptionIfPostByTitleNotFound(){
      //  when(postRepository.findByTitleContainingIgnoreCase(anyString())).thenReturn(Optional.empty());
      //  postService.getPostByTitle(anyString());
    }

    @Test
    public void savePost() {
        //User user = User.builder().id(1L).build();

        Post post = Post.builder().build();
        //post.setUser(user);
        when(postRepository.save(any())).thenReturn(post);
        Post savedPost = postService.createPost(post);
        assertNotNull("Post is Not saved", savedPost);
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void updatePost() {
        Post postToUpdate = Post.builder().build();
        Optional<Post> postOptional = Optional.of(postToUpdate);
        Post postUpdateData = Post.builder().build();
        when(postRepository.findById(anyLong())).thenReturn(postOptional);
        when(postRepository.save(any())).thenReturn(postToUpdate);
        Post savedUpdatedPost = postService.updatePost(postUpdateData, anyLong());
        assertNotNull(savedUpdatedPost);
    }

    @Test
    public void locationCreatedIfNull(){
        Post postToUpdate = Post.builder().build();
        Optional<Post> postOptional = Optional.of(postToUpdate);
        Post postUpdateData = Post.builder().build();
        when(postRepository.findById(anyLong())).thenReturn(postOptional);
        when(postRepository.save(any())).thenReturn(postToUpdate);
        postService.updatePost(postUpdateData, anyLong());
        assertNotNull(postToUpdate.getLocation());
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
      //  List<Post> foundUserPosts = postService.getPostsByUserId(anyLong());
      //  assertEquals(foundUserPosts.size(), 2);
    }

    @Test(expected = UserNotFoundException.class)
    public void throwExceptionIfUserNotFound(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
      //  postService.getPostsByUserId(anyLong());
    }
}