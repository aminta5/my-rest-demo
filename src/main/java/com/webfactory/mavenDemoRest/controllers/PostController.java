package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

//@EnableResourceServer
@RestController
public class PostController {

    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;

    public PostController(PostDaoService postDaoService, UserDaoService userDaoService) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
    }

    //find all posts
    /*@GetMapping(path = "/posts")
    public List<Post> getPosts(){


        return postDaoService.findAllPosts();
    }*/

    @GetMapping(path = "/posts")
    public List<Post> getPosts(Authentication authentication){

        String username = authentication.getName();
        User user = userDaoService.findUserByNickname(username);
        return postDaoService.findPostsByUserId(user.getId());
    }

    //find specific post (by id)
    @GetMapping(path = "/posts/{id}")
    public Post findPostById(@PathVariable String id){
        Optional<Post> optionalPost = postDaoService.findAllPosts().stream().filter(p -> p.getId() == Long.parseLong(id)).findFirst();
        return optionalPost.orElse(null);
    }

    //delete post
    @DeleteMapping(path = "/posts/{id}/delete")
    public List<Post> deletePost(@PathVariable String id){
        Post post = postDaoService.findPostById(Long.parseLong(id));
        postDaoService.deletePost(post);
        return postDaoService.findAllPosts();
    }

    //create post
    @PostMapping(path="/posts/create")
    public ResponseEntity<Object> createPost(@RequestBody RequestBodyPost requestBodyPost){
        Post savedPost = postDaoService.savePost(requestBodyPost);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update post
    @PutMapping(path = "/posts/update/{postId}")
    public ResponseEntity<Object> updatePost(@RequestBody RequestBodyPost requestBodyPost, @PathVariable String postId){
        Post savedPost = postDaoService.updatePost(requestBodyPost, Long.parseLong(postId));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


}
