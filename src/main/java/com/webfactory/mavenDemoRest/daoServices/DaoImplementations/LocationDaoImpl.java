package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.LocationDaoService;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationDaoImpl implements LocationDaoService {

    private final LocationRepository locationRepository;

    public LocationDaoImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAllLocations() {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAll().forEach(locations::add);
        return locations;
    }

}
