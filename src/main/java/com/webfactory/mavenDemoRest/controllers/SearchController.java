package com.webfactory.mavenDemoRest.controllers;

import com.webfactory.mavenDemoRest.daoServices.LocationDaoService;
import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
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
    private final PostDaoService postDaoService;
    private final UserDaoService userDaoService;
    private final LocationDaoService locationDaoService;

    public SearchController(PostDaoService postDaoService, UserDaoService userDaoService, LocationDaoService locationDaoService) {
        this.postDaoService = postDaoService;
        this.userDaoService = userDaoService;
        this.locationDaoService = locationDaoService;
    }

    //Search Users by nickname
    @GetMapping(path = "/users/find/{nickname}")
    public User findUsersByNickname(@PathVariable String nickname) {
        return userDaoService.findUserByNickname(nickname);
    }

    //Search Users by Location
    @GetMapping(path = "/users/location/{city}")
    public List<User> findUsersByLocation(@PathVariable String city) {
        if(!locationDaoService.findLocationByCity(city).getUsers().isEmpty()){
            return locationDaoService.findLocationByCity(city).getUsers();
        }
        throw new UserNotFoundException(city);
    }

    //Search Posts by title
    @GetMapping(path = "/posts/find/{title}")
    public List<Post> findPostsByTitle(@PathVariable String title) {
        return postDaoService.findPostByTitle(title);
    }

    //Search Posts by Location
    @GetMapping(path = "/posts/location/{city}")
    public List<Post> findPostsByLocation(@PathVariable String city) {
        if(!locationDaoService.findLocationByCity(city).getPosts().isEmpty()){
            return locationDaoService.findLocationByCity(city).getPosts();
        }
        throw new PostNotFoundException(city);

    }
}
