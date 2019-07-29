package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class PostController {

    PostDaoService postDaoService;

    public PostController(PostDaoService postDaoService) {
        this.postDaoService = postDaoService;
    }
    @GetMapping(path = "/users/posts")
    public List<Post> getPosts(){

        return postDaoService.findAllPosts();
    }
}
