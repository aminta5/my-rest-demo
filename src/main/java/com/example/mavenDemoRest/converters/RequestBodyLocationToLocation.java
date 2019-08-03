package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.requestBodies.RequestBodyLocation;
import com.example.mavenDemoRest.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//@Lazy
@Component
public class RequestBodyLocationToLocation implements Converter<RequestBodyLocation, Location> {
    private RequestBodyUserToUser requestBodyUserToUser;
    private RequestBodyPostToPost requestBodyPostToPost;


    //constructors
    /*public LocationCommandToLocation(UserCommandToUser userCommandToUser, PostCommandToPost postCommandToPost) {
        this.userCommandToUser = userCommandToUser;
        this.postCommandToPost = postCommandToPost;
    }*/

    public RequestBodyLocationToLocation() {
    }

    public RequestBodyUserToUser getRequestBodyUserToUser() {
        return requestBodyUserToUser;
    }

    @Autowired
    public void setRequestBodyUserToUser(RequestBodyUserToUser requestBodyUserToUser) {
        this.requestBodyUserToUser = requestBodyUserToUser;
    }

    public RequestBodyPostToPost getRequestBodyPostToPost() {
        return requestBodyPostToPost;
    }

    @Autowired
    public void setRequestBodyPostToPost(RequestBodyPostToPost requestBodyPostToPost) {
        this.requestBodyPostToPost = requestBodyPostToPost;
    }

    @Override
    public Location convert(RequestBodyLocation source) {
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
            source.getUsers().forEach(userCommand -> location.getUsers().add(requestBodyUserToUser.convert(userCommand)));
        }
        if(source.getPosts() != null && source.getPosts().size() > 0){
            source.getPosts().forEach(postCommand -> location.getPosts().add(requestBodyPostToPost.convert(postCommand)));
        }
        return location;
    }
}
