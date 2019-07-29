package com.example.mavenDemoRest.model;

import com.example.mavenDemoRest.constants.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String password;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> post = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    private Location location;
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    //constructors


    public User() {
    }

    public User(String firstName, String lastName, String nickname, String email, String password, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    //@Builder
    public User(String firstName, String lastName, String nickname, String email, String password, List<Post> post, Location location, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.post = post;
        this.location = location;
        this.userType = userType;
    }

    //getters and setters
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Post> getPosts() {
        return post;
    }

    public void setPosts(List<Post> post) {
        this.post = post;
    }
}
