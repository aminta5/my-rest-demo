package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.services.LocationService;
import com.webfactory.mavenDemoRest.exceptions.LocationNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    @Cacheable(value = "locationsByCity", key = "#city")
    public Location getLocationByCity(String city) {
        return locationRepository.findByCityContainingIgnoreCase(city).orElseThrow(() -> new LocationNotFoundException(city));
    }

}
