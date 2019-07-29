package com.example.mavenDemoRest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post extends BaseEntity{

    //title required max 120chars
    @Column
    private String title;

    //description 1000 chars max
    @Column
    private String description;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
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
