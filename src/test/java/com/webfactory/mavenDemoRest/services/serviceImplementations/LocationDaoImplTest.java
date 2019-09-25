package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.exceptions.LocationNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.repositories.LocationRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class LocationDaoImplTest {
    private LocationServiceImpl locationService;

    @Mock
    LocationRepository locationRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        locationService = new LocationServiceImpl(locationRepository);
    }

    @Test
    public void findLocationByCity() {
        Location location = new Location();
        location.setCity("Skopje");
        Optional<Location> locationOptional = Optional.of(location);
        when(locationRepository.findByCityContainingIgnoreCase(anyString())).thenReturn(locationOptional);
        Location foundLocation = locationService.getLocationByCity(anyString());
        assertNotNull("Location by city not found", foundLocation);
        verify(locationRepository, times(1)).findByCityContainingIgnoreCase(anyString());
    }

    @Test(expected = LocationNotFoundException.class)
    public void throwExceptionIfLocationNotFound() {
        when(locationRepository.findByCityContainingIgnoreCase(anyString())).thenReturn(Optional.empty());
        locationService.getLocationByCity(anyString());
    }
}