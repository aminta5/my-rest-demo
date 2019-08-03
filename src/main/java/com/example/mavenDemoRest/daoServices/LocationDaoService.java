package com.example.mavenDemoRest.daoServices;

import com.example.mavenDemoRest.model.Location;

import java.util.List;

public interface LocationDaoService {
    //List<User> findUsersByLocation(Location location);
   //List<Post> findPostsByLocation(Location location);

    List<Location> findAllLocations();
}
