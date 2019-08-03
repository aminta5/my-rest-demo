package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.commands.PostCommand;
//import com.example.mavenDemoRest.converters.PostCommandToPost;
import com.example.mavenDemoRest.converters.PostCommandToPost;
import com.example.mavenDemoRest.converters.UserCommandToUser;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
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
    private final UserDaoService userDaoService;
    private final PostCommandToPost postCommandToPost;
    private final UserCommandToUser userCommandToUser;

    public PostController(PostDaoService postDaoService, UserDaoService userDaoService, PostCommandToPost postCommandToPost, UserCommandToUser userCommandToUser) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
        this.postCommandToPost = postCommandToPost;
        this.userCommandToUser = userCommandToUser;
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
    public ResponseEntity<Object> createPost(@RequestBody PostCommand post){
        User user = userDaoService.findUserById(post.getUserId());
        Post newPost = postCommandToPost.convert(post);
        newPost.setUser(user);
        Post savedPost = postDaoService.createPost(newPost);

        /*User user = userCommandToUser.convert(post.getUser());
        user.getPosts().add(postCommandToPost.convert(post));
        Post savedPost = postDaoService.createPost(postCommandToPost.convert(post));*/

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    //update post
    @PutMapping(path = "/posts/update/{postId}")
    public ResponseEntity<Object> updatePost(@RequestBody PostCommand postCommand, @PathVariable String postId){
        Post postToUpdate = postDaoService.findPostById(Long.parseLong(postId));
        Post post = postCommandToPost.convert(postCommand);

        //update post
        if(post.getTitle() != null){
            postToUpdate.setTitle(post.getTitle());
        }
        if(post.getDescription() != null){
            postToUpdate.setDescription(post.getDescription());
        }

        //update of the location
        Location locationToUpdate = postToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = post.getLocation();
        //System.out.println(locationToUpdate + " location" + " " + post.getLocation().getCity());

        if(newLocation != null){
            if(newLocation.getCity() != null){
                locationToUpdate.setCity(newLocation.getCity());
            }
            if(newLocation.getCountry() != null){
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if(newLocation.getLongitude() != null && newLocation.getLongitude() != 0.0){
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if(newLocation.getLatitude() != null && newLocation.getLatitude() != 0.0){
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }


        postToUpdate.setLocation(locationToUpdate);
        /*User user = userDaoService.findUserById(Long.parseLong(user_id));
        Post postToDelete = postDaoService.findPostById(Long.parseLong(post_id));
        user.getPosts().remove(postToDelete);
        user.getPosts().add(post);*/
        //User savedUser = userDaoService.createUser(user);

        Post savedPost = postDaoService.createPost(postToUpdate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


}
