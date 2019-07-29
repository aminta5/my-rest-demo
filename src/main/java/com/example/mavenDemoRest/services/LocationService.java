package com.example.mavenDemoRest.services;

import com.example.mavenDemoRest.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationService {
    private static LocationService instance = new LocationService();
    private LocationService(){}

    public static LocationService getInstance(){
        return instance;
    }
    //the same thing with builder pattern or args constructor
    public Location createLocation(long id, String city, String country, float longitude, float latitude){
        Location location = new Location();
        //location.setId(id);
        location.setCity(city);
        location.setCountry(country);
        location.setLongitude(longitude);
        location.setLatitude(latitude);

        return location;
    }
}
