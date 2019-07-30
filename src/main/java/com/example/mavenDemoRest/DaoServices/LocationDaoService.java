package com.example.mavenDemoRest.DaoServices;

import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;

import java.util.List;

public interface LocationDaoService {
    //List<User> findUsersByLocation(Location location);
   //List<Post> findPostsByLocation(Location location);

    List<Location> findAllLocations();
}
