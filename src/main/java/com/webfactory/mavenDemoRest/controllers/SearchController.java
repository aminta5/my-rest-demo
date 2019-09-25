package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.services.LocationService;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    private final PostService postService;
    private final UserService userService;
    private final LocationService locationService;

    public SearchController(PostService postService, UserService userService, LocationService locationService) {
        this.postService = postService;
        this.userService = userService;
        this.locationService = locationService;
    }

    //Search Users by nickname
    @GetMapping(path = "/users/find/{nickname}")
    public User findUsersByNickname(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }

    //Search Users by Location
    @GetMapping(path = "/users/location/{city}")
    public List<User> findUsersByLocation(@PathVariable String city) {
        if (!locationService.getLocationByCity(city).getUsers().isEmpty()) {
            return locationService.getLocationByCity(city).getUsers();
        }
        throw new UserNotFoundException(city);
    }

    //Search Posts by title
    @GetMapping(path = "/posts/find/{title}")
    public List<Post> findPostsByTitle(@PathVariable String title) {
        return postService.getPostByTitle(title);
    }

    //Search Posts by Location
    @GetMapping(path = "/posts/location/{city}")
    public List<Post> findPostsByLocation(@PathVariable String city) {
        if (!locationService.getLocationByCity(city).getPosts().isEmpty()) {
            return locationService.getLocationByCity(city).getPosts();
        }
        throw new PostNotFoundException(city);
    }
}
