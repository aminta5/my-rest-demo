package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final RequestBodyPostToPost requestBodyPostToPost;

    public PostController(PostService postService, UserService userService, RequestBodyPostToPost requestBodyPostToPost) {
        this.postService = postService;
        this.userService = userService;
        this.requestBodyPostToPost = requestBodyPostToPost;
    }

    @GetMapping(path = "/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<Post> getAllPosts() {
        return postService.getAllPosts(PageRequest.of(0,2));
    }

    //show posts from authenticated user
    @GetMapping(path = "/users/{userId}/posts")

    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.authorizedUser(authentication.name, #userId)")
    public List<Post> getUsersPosts(@P("userId") @PathVariable Long userId) {
        return postService.getPostsByUserId(userId);
    }

    //find specific post (by id)
    @GetMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public Post findPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    //delete post(authenticated user or admin)
    @DeleteMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);
    }

    //create post
    @PostMapping(path = "/posts/new")
    public ResponseEntity<Post> createPost(@Valid @RequestBody RequestBodyPost requestBodyPost, Authentication authentication) {
        User user = userService.getUserByNickname(authentication.getName());
        requestBodyPost.setUserId(user.getId());
        Post savedPost = postService.createPost(requestBodyPostToPost.convert(requestBodyPost));

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    //update post
    @PutMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public Post updatePost(@Valid @RequestBody RequestBodyPost requestBodyPost, @P("postId") @PathVariable Long postId) {
        return postService.updatePost(requestBodyPostToPost.convert(requestBodyPost), postId);
    }
}
