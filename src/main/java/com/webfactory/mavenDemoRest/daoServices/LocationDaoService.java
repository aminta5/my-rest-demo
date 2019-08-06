package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.Location;

import java.util.List;

public interface LocationDaoService {
    //List<User> findUsersByLocation(Location location);
   //List<Post> findPostsByLocation(Location location);

    List<Location> findAllLocations();
}
