package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.List;

@RestController
public class PostController {

    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;
    private final RequestBodyPostToPost requestBodyPostToPost;

    public PostController(PostDaoService postDaoService, UserDaoService userDaoService, RequestBodyPostToPost requestBodyPostToPost) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
        this.requestBodyPostToPost = requestBodyPostToPost;
    }

    @GetMapping(path = "/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Post> getAllPosts() {
        return postDaoService.findAllPosts();
    }

    //show posts from authenticated user
    @GetMapping(path = "/users/{userId}/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.authorizedUser(authentication, #userId)")
    public List<Post> getUsersPosts(@P("userId") @PathVariable Long userId) {
        return postDaoService.findPostsByUserId(userId);
    }

    //find specific post (by id)
    @GetMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public Post findPostById(@P("postId") @PathVariable Long postId) {
        return postDaoService.findPostById(postId);
    }

    //delete post(authenticated user or admin)
    @DeleteMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public void deletePost(@P("postId") @PathVariable Long postId) {
        postDaoService.deletePostById(postId);
    }

    //create post
    @PostMapping(path = "/posts/new")
    public ResponseEntity<Post> createPost(@Valid @RequestBody RequestBodyPost requestBodyPost, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        requestBodyPost.setUserId(user.getId());
        Post savedPost = postDaoService.savePost(requestBodyPostToPost.convert(requestBodyPost));

        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    //update post
    @PutMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public Post updatePost(@Valid @RequestBody RequestBodyPost requestBodyPost, @P("postId") @PathVariable Long postId) {
        return postDaoService.updatePost(requestBodyPostToPost.convert(requestBodyPost), postId);
    }
}
