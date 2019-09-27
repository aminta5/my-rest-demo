package com.webfactory.mavenDemoRest.services;


import com.webfactory.mavenDemoRest.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Page<Post> getAllPosts(Pageable pageable);

    Post getPostById(Long Id);

    List<Post> getPostByTitle(String title);

    Post createPost(Post newPost);

    Post updatePost(Post postUpdateObject, Long postId);

    void deletePostById(Long id);

    List<Post> getPostsByUserId(Long userId);

}

