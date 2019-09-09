package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
public class PostController {

    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;

    public PostController(PostDaoService postDaoService, UserDaoService userDaoService) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Post> getAllPosts() {
        return postDaoService.findAllPosts();
    }

    //show posts from authenticated user
    @GetMapping(path = "/users/{userId}/posts")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.authorizedUser(authentication, #userId)")
    public List<Post> getUsersPosts(@P("userId") @PathVariable Long userId, Authentication authentication) {
            return postDaoService.findPostsByUserId(userId);
    }

    //find specific post (by id)
    @GetMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public Post findPostById(@P("postId") @PathVariable Long postId/*, Authentication authentication*/) {
        return postDaoService.findPostById(postId);
    }

    //delete post(authenticated user or admin)
    @DeleteMapping(path = "/posts/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public void deletePost(@P("postId") @PathVariable Long postId, Authentication authentication) {
        postDaoService.deletePostById(postId);
    }

    //create post
    @PostMapping(path = "/posts/new")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or @accessManager.postCanBeUpdatedOrSeen(authentication, #postId)")
    public ResponseEntity<Object> updatePost(@RequestBody RequestBodyPost requestBodyPost, @P("postId") @PathVariable Long postId/*, @P("authentication") Authentication authentication*/) {
        Post savedPost = postDaoService.updatePost(requestBodyPost, postId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
