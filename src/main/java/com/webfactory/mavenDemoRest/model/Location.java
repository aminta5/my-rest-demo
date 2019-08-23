package com.webfactory.mavenDemoRest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location extends BaseEntity{

    //city max 120 chars
    @NotNull
    @Size(max = 120)
    @Column(name = "city")
    private String city;

    //country max 120 chars
    @NotNull
    @Size(max = 120)
    @Column
    private String country;

    @NotNull
    @Column
    private Float longitude;

    @NotNull
    @Column
    private Float latitude;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<User> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<Post> posts = new ArrayList<>();

    //constructors

    public Location() {
    }

    public Location(String city, String country, float longitude, float latitude) {
        this.city = city;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //@Builder
    public Location(String city, String country, float longitude, float latitude, List<User> users, List<Post> posts) {
        this.city = city;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.users = users;
        this.posts = posts;
    }

    // getters and setters

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
    public Float getLongitude() {
            return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
