package com.webfactory.mavenDemoRest.services;


import com.webfactory.mavenDemoRest.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(Long Id);

    List<Post> getPostByTitle(String title);

    Post createPost(Post newPost);

    Post updatePost(Post postUpdateObject, Long postId);

    void deletePostById(Long id);

    List<Post> getPostsByUserId(Long userId);

}

