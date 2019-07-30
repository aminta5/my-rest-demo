package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.commands.LocationCommand;
import com.example.mavenDemoRest.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Lazy
@Component
public class LocationCommandToLocation implements Converter<LocationCommand, Location> {
    private  UserCommandToUser userCommandToUser;
    private  PostCommandToPost postCommandToPost;


    //constructors
    /*public LocationCommandToLocation(UserCommandToUser userCommandToUser, PostCommandToPost postCommandToPost) {
        this.userCommandToUser = userCommandToUser;
        this.postCommandToPost = postCommandToPost;
    }*/

    public LocationCommandToLocation() {
    }

    public UserCommandToUser getUserCommandToUser() {
        return userCommandToUser;
    }

    @Autowired
    public void setUserCommandToUser(UserCommandToUser userCommandToUser) {
        this.userCommandToUser = userCommandToUser;
    }

    public PostCommandToPost getPostCommandToPost() {
        return postCommandToPost;
    }

    @Autowired
    public void setPostCommandToPost(PostCommandToPost postCommandToPost) {
        this.postCommandToPost = postCommandToPost;
    }

    @Override
    public Location convert(LocationCommand source) {
        if(source == null){
            return null;
        }
        final Location  location = new Location();
        location.setId(source.getId());
        location.setCity(source.getCity());
        location.setCountry(source.getCountry());
        location.setLongitude(source.getLongitude());
        location.setLatitude(source.getLatitude());
        if(source.getUsers() != null && source.getUsers().size() > 0){
            source.getUsers().forEach(userCommand -> location.getUsers().add(userCommandToUser.convert(userCommand)));
        }
        if(source.getPosts() != null && source.getPosts().size() > 0){
            source.getPosts().forEach(postCommand -> location.getPosts().add(postCommandToPost.convert(postCommand)));
        }
        return location;
    }
}
