package com.example.mavenDemoRest.DaoServices;

import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface PostDaoService {
    List<Post> findAllPosts();
    Post findPostById(Long Id);
    Location findPostLocation(Post post);
    List<Post> findPostByTitle(String title);
    Post createPost(Post post);
    Post updatePost(Post post);
    void deletePost(Post post);

}
