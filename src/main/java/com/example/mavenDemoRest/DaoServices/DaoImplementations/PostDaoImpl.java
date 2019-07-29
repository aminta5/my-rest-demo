package com.example.mavenDemoRest.DaoServices.DaoImplementations;

import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostDaoImpl implements PostDaoService {

    PostRepository postRepository;

    //constructor injection

    public PostDaoImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAllPosts(){
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts :: add);
        System.out.println(posts);
        return posts;
    }

    @Override
    public Post findPostById(Long id){
       return postRepository.findById(id).orElse(null);
    }

    @Override
    public Location findPostLocation(Post post){
        return post.getLocation();
    }


    @Override
    public List<Post> findPostByTitle(String title){
        List<Post> posts = new ArrayList<>();
        findAllPosts().stream().filter(p -> p.getTitle() == title).forEach(posts :: add);
        return posts;

    }

    @Override
    public Post createPost(Post post){
        return postRepository.save(post);
    }

    //TODO
    @Override
    public Post updatePost(Post post){
        return null;
    }

    @Override
    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
