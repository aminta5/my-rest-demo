package com.example.mavenDemoRest.commands;

import com.example.mavenDemoRest.constants.UserType;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import java.util.ArrayList;
import java.util.List;

public class UserCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String email;
    private String password;
    private List<PostCommand> posts = new ArrayList<>();
    private LocationCommand location;
    private UserType userType;

    //constructors
    public UserCommand() {
    }

    public UserCommand(Long id, String firstName, String lastName, String nickname, String email, String password, List<PostCommand> posts, LocationCommand location, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.location = location;
        this.userType = userType;
    }

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<PostCommand> getPost() {
        return posts;
    }

    public void setPost(List<PostCommand> post) {
        this.posts = post;
    }

    public LocationCommand getLocation() {
        return location;
    }

    public void setLocation(LocationCommand location) {
        this.location = location;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
