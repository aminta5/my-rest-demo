package com.example.mavenDemoRest.controllers;

import com.example.mavenDemoRest.DaoServices.LocationDaoService;
import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.DaoServices.UserDaoService;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@EnableResourceServer
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
    @GetMapping(path="/users/find/{nickname}")
    public List<User> findUsersByNickname(@PathVariable String nickname){
        return userDaoService.findUserByNickname(nickname);
    }

    //Search Users by Location
    @GetMapping(path="/users/location/{city}")
    public List<User> findUsersByLocation(@PathVariable String city){
        Optional<Location> optinalLocation = locationDaoService.findAllLocations().stream().filter(l -> l.getCity().equalsIgnoreCase(city)).findFirst();
        if(optinalLocation.isPresent()){
            return optinalLocation.get().getUsers();
        }
        else{
            return null;
        }
    }
    //Search Posts by title
    @GetMapping(path="/posts/find/{title}")
    public List<Post> findPostsByTitle(@PathVariable String title){
        return postDaoService.findPostByTitle(title);
    }

    //Search Posts by Location
    @GetMapping(path = "/posts/location/{city}")
    public List<Post> findPostsByLocation(@PathVariable String city){
        Optional<Location> optinalLocation = locationDaoService.findAllLocations().stream().filter(l -> l.getCity().equalsIgnoreCase(city)).findFirst();
        if(optinalLocation.isPresent()){
            return optinalLocation.get().getPosts();
        }
        else{
            return null;
        }
    }
}
