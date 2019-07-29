package com.example.mavenDemoRest.DaoServices.DaoImplementations;

import com.example.mavenDemoRest.DaoServices.LocationDaoService;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationDaoImpl implements LocationDaoService {

    LocationRepository locationRepository;

    public LocationDaoImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<User> findUsersByLocation(Location location){
        return location.getUsers();
    }

    @Override
    public List<Post> findPostsByLocation(Location location){
        return location.getPosts();
    }
}
