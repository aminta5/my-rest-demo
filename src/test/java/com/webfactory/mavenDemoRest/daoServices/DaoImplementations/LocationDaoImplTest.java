package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LocationDaoImplTest {

    private LocationDaoImpl locationService;

    @Mock
    LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        locationService = new LocationDaoImpl(locationRepository);
    }

    @Test
    void findLocationByCity() {
        Location location = new Location();
        location.setCity("Skopje");
        Optional<Location> locationOptional = Optional.of(location);
        when(locationRepository.findByCityContainingIgnoreCase(anyString())).thenReturn(locationOptional);
        Location foundLocation = locationService.findLocationByCity(anyString());
        assertNotNull(foundLocation, "Location by city not found");
        verify(locationRepository, times(1)).findByCityContainingIgnoreCase(anyString());
    }
}