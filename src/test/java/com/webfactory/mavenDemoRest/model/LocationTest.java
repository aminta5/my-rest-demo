package com.webfactory.mavenDemoRest.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    Location location;

    @Before
    public void setUp(){
        location = new Location();
    }

    @Test
    public void getCity() {
        String city = "Paris";
        location.setCity(city);
        assertEquals(city, location.getCity());
    }

    @Test
    public void getCountry() {
        String country = "Andora";
        location.setCountry(country);
        assertEquals(country, location.getCountry());
    }

    /*@Test
    public void getLongitude() {
        float lon = 23.3f;
        location.setLongitude(lon);
        assertEquals(lon, location.getLongitude());
    }

    @Test
    public void getLatitude() {
        float lat = 44.5f;
        location.setLatitude(lat);
        assertEquals(lat, location.getLatitude());
    }*/
}