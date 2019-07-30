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

    private final LocationRepository locationRepository;

    public LocationDaoImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAllLocations(){
        List<Location> locations = (List)locationRepository.findAll();
        return locations;
    }

}
