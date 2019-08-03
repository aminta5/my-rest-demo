package com.example.mavenDemoRest.DaoServices;

import com.example.mavenDemoRest.commands.PostCommand;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface PostDaoService {
    List<Post> findAllPosts();
    Post findPostById(Long Id);
    Location findPostLocation(Post post);
    List<Post> findPostByTitle(String title);
    Post savePost(PostCommand postCommand);
    Post updatePost(PostCommand postCommand, Long postId);
    void deletePost(Post post);

}

