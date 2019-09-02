package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.Post;

import java.util.List;

public interface PostDaoService {
    List<Post> findAllPosts();
    Post findPostById(Long Id);
    Location findPostLocation(Post post);
    List<Post> findPostByTitle(String title);
    Post savePost(RequestBodyPost requestBodyPost);
    Post updatePost(RequestBodyPost requestBodyPost, Long postId);
    void deletePost(Post post);
    List<Post> findPostsByUserId(Long id);

}

