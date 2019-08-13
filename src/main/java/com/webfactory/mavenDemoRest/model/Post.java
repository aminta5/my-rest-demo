package com.webfactory.mavenDemoRest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    //title required max 120chars
    @Size(max = 120)
    @Column
    private String title;

    //description 1000 chars max
    @Size(max = 1000)
    @Column
    private String description;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    //@JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    private Location location;

    //constructors


    public Post() {
    }

    public Post(String title) {
        this.title = title;
        this.description = description;
    }

    //@Builder
    public Post(String title, String description, User user, Location location) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.location = location;
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
