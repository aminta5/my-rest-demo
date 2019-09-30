package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.services.LocationService;
import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Page<User> findUsersByLocation(@PathVariable String city) {
        if (!locationService.getLocationByCity(city).getUsers().isEmpty()) {
            return new PageImpl<>(locationService.getLocationByCity(city).getUsers());
        }
        throw new UserNotFoundException(city);
    }

    //Search Posts by title
    @GetMapping(path = "/posts/find/{title}")
    public Page<Post> findPostsByTitle(@PathVariable String title,
                                       @RequestParam("page") int page,
                                       @RequestParam("size") int size) {
        return postService.getPostByTitle(title, PageRequest.of(page, size));
    }

    //Search Posts by Location
    @GetMapping(path = "/posts/location/{city}")
    public Page<Post> findPostsByLocation(@PathVariable String city,
                                          @RequestParam("page") int page,
                                          @RequestParam("size") int size) {
        if (!locationService.getLocationByCity(city).getPosts().isEmpty()) {
            List<Post> posts = locationService.getLocationByCity(city).getPosts();
            return new PageImpl<>(posts, PageRequest.of(page, size), posts.size());
        }
        throw new PostNotFoundException(city);
    }
}
