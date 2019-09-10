package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.LocationDaoService;
import com.webfactory.mavenDemoRest.exceptions.LocationNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationDaoImpl implements LocationDaoService {

    private final LocationRepository locationRepository;

    public LocationDaoImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findLocationByCity(String city) {
        return locationRepository.findByCityContainingIgnoreCase(city).orElseThrow(() -> new LocationNotFoundException(city));
    }

}
