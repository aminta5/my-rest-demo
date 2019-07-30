package com.example.mavenDemoRest.commands;

import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import java.util.ArrayList;
import java.util.List;

public class LocationCommand {
    private Long id;
    private String city;
    private String country;
    private float longitude;
    private float latitude;
    private List<UserCommand> users = new ArrayList<>();
    private List<PostCommand> posts = new ArrayList<>();

    //constructors
    public LocationCommand() {
    }

    public LocationCommand(Long id, String city, String country, float longitude, float latitude, List<UserCommand> users, List<PostCommand> posts) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.users = users;
        this.posts = posts;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public List<UserCommand> getUsers() {
        return users;
    }

    public void setUsers(List<UserCommand> users) {
        this.users = users;
    }

    public List<PostCommand> getPosts() {
        return posts;
    }

    public void setPosts(List<PostCommand> posts) {
        this.posts = posts;
    }
}
