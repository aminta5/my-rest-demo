package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.commands.UserCommand;
import com.example.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Lazy
@Component
public class UserCommandToUser implements Converter<UserCommand, User> {
    private  PostCommandToPost postCommandToPost;
    private  LocationCommandToLocation locationCommandToLocation;

    /*public UserCommandToUser(PostCommandToPost postCommandToPost, LocationCommandToLocation locationCommandToLocation) {
        this.postCommandToPost = postCommandToPost;
        this.locationCommandToLocation = locationCommandToLocation;
    }*/

    //getters and setters


    public PostCommandToPost getPostCommandToPost() {
        return postCommandToPost;
    }

    @Autowired
    public void setPostCommandToPost(PostCommandToPost postCommandToPost) {
        this.postCommandToPost = postCommandToPost;
    }

    public LocationCommandToLocation getLocationCommandToLocation() {
        return locationCommandToLocation;
    }

    @Autowired
    public void setLocationCommandToLocation(LocationCommandToLocation locationCommandToLocation) {
        this.locationCommandToLocation = locationCommandToLocation;
    }

    @Override
    public User convert(UserCommand source) {
        if(source == null){
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setNickname(source.getNickname());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        if(source.getPost() != null && source.getPost().size() > 0){
            source.getPost().forEach(postCommand -> user.getPosts().add(postCommandToPost.convert(postCommand)));
        }
        user.setLocation(locationCommandToLocation.convert(source.getLocation()));
        user.setUserType(source.getUserType());

        return user;

    }
}
