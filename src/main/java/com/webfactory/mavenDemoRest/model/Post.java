package com.webfactory.mavenDemoRest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    //title required max 120chars
    @NotNull
    @Size(max = 120)
    @Column(name = "title")
    private String title;

    //description 1000 chars max
    @NotNull
    @Size(max = 1000)
    @Column
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)/*(cascade=CascadeType.ALL)*/
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;

    //constructors
    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

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
