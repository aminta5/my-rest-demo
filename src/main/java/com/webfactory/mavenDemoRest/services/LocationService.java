package com.webfactory.mavenDemoRest.services;

import com.webfactory.mavenDemoRest.model.Location;


public interface LocationService {
    Location getLocationByCity(String city);
}
