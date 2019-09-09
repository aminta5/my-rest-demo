package com.webfactory.mavenDemoRest.daoServices;

import com.webfactory.mavenDemoRest.model.Location;


public interface LocationDaoService {
    Location findLocationByCity(String city);
}
