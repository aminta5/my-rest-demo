package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.daoServices.PostDaoService;
import com.example.mavenDemoRest.daoServices.UserDaoService;
import com.example.mavenDemoRest.requestBodies.RequestBodyPost;
//import com.example.mavenDemoRest.converters.PostCommandToPost;
import com.example.mavenDemoRest.converters.RequestBodyPostToPost;
import com.example.mavenDemoRest.converters.RequestBodyUserToUser;
import com.example.mavenDemoRest.model.Post;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@Lazy
@RestController
public class PostController {

    private final PostDaoService postDaoService;

    public PostController(PostDaoService postDaoService) {
        this.postDaoService = postDaoService;
    }

    //find all posts
    @GetMapping(path = "/posts")
    public List<Post> getPosts(){

        return postDaoService.findAllPosts();
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
