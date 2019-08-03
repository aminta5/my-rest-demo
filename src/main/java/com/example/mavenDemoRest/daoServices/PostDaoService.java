package com.example.mavenDemoRest.daoServices;

import com.example.mavenDemoRest.requestBodies.RequestBodyPost;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;

import java.util.List;

public interface PostDaoService {
    List<Post> findAllPosts();
    Post findPostById(Long Id);
    Location findPostLocation(Post post);
    List<Post> findPostByTitle(String title);
    Post savePost(RequestBodyPost requestBodyPost);
    Post updatePost(RequestBodyPost requestBodyPost, Long postId);
    void deletePost(Post post);

}

