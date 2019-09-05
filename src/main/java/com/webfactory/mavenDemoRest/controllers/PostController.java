package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.helpers.Helper;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;

    public PostController(PostDaoService postDaoService, UserDaoService userDaoService) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/posts")
    public List<Post> getAllPosts() {
        return postDaoService.findAllPosts();
    }

    //show posts from authenticated user
    @GetMapping(path = "/users/{userId}/posts")
    public List<Post> getUsersPosts(@PathVariable String userId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        if (Helper.isSameUser(user, Long.parseLong(userId))) {
            return postDaoService.findPostsByUserId(user.getId());
        }
        throw new RuntimeException("User Not authorized");
    }

    //find specific post (by id)
    @GetMapping(path = "/posts/{postId}")
    public Post findPostById(@PathVariable String postId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        Post post = postDaoService.findPostById(Long.parseLong(postId));
        if (Helper.isAdmin(user) || Helper.isUserPost(post, user)) {
            return post;
        }
        throw new RuntimeException("User Not Authorized");
    }

    //authenticated user delete his own posts
    @DeleteMapping(path = "/posts/{postId}")
    public List<Post> deletePost(@PathVariable String postId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        Post post = postDaoService.findPostById(Long.parseLong(postId));
        if (Helper.isAdmin(user)) {
            postDaoService.deletePost(post);
            return postDaoService.findAllPosts();
        }
        if (Helper.isUserPost(post, user)) {
            postDaoService.deletePost(post);
            user.getPosts().remove(post);
            return user.getPosts();
        }
        throw new RuntimeException("User Not Authorized");

    }

    //create post
    @PostMapping(path = "/posts/create")
    public ResponseEntity<Object> createPost(@RequestBody RequestBodyPost requestBodyPost, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        requestBodyPost.setUserId(user.getId());
        Post savedPost = postDaoService.savePost(requestBodyPost);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update post
    @PutMapping(path = "/posts/{postId}")
    @PreAuthorize("hasPermission(postId, '')")
    public ResponseEntity<Object> updatePost(@RequestBody RequestBodyPost requestBodyPost, @PathVariable Long postId, Authentication authentication) {
        User user = userDaoService.findUserByNickname(authentication.getName());
        Post savedPost = new Post();
        if (Helper.isUserPost(postDaoService.findPostById(postId), user)) {
            savedPost = postDaoService.updatePost(requestBodyPost, postId);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


}
