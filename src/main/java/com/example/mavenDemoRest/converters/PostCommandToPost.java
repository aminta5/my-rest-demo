package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.commands.PostCommand;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostCommandToPost implements Converter<PostCommand, Post> {
    private  LocationCommandToLocation locationCommandToLocation;
    private  UserCommandToUser userCommandToUser;

    /*public PostCommandToPost(LocationCommandToLocation locationCommandToLocation, UserCommandToUser userCommandToUser) {
        this.locationCommandToLocation = locationCommandToLocation;
        this.userCommandToUser = userCommandToUser;
    }*/

    //getter and setter


    public LocationCommandToLocation getLocationCommandToLocation() {
        return locationCommandToLocation;
    }

    @Autowired
    public void setLocationCommandToLocation(LocationCommandToLocation locationCommandToLocation) {
        this.locationCommandToLocation = locationCommandToLocation;
    }

    public UserCommandToUser getUserCommandToUser() {
        return userCommandToUser;
    }

    @Autowired
    public void setUserCommandToUser(UserCommandToUser userCommandToUser) {
        this.userCommandToUser = userCommandToUser;
    }

    @Override
    public Post convert(PostCommand source) {
        if(source == null){
            return null;
        }

        final Post post = new Post();
        post.setId(source.getId());
        post.setTitle(source.getTitle());
        post.setDescription(source.getDescription());

        //post.setUser(userCommandToUser.convert(source.getUser()));
        if(source.getId() != null){
            User user = new User();
            user.setId(source.getUserId());
            post.setUser(user);
            user.getPosts().add(post);
        }
        post.setLocation(locationCommandToLocation.convert(source.getLocation()));
        return post;
    }
}
