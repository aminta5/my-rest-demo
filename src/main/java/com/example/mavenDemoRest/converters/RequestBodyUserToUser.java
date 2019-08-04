package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.requestBodies.RequestBodyUser;
import com.example.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class RequestBodyUserToUser implements Converter<RequestBodyUser, User> {
    private RequestBodyPostToPost requestBodyPostToPost;
    private RequestBodyLocationToLocation requestBodyLocationToLocation;

    //setters for injection

    @Autowired
    public void setRequestBodyPostToPost(RequestBodyPostToPost requestBodyPostToPost) {
        this.requestBodyPostToPost = requestBodyPostToPost;
    }

    @Autowired
    public void setRequestBodyLocationToLocation(RequestBodyLocationToLocation requestBodyLocationToLocation) {
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
    }

    @Override
    public User convert(RequestBodyUser source) {
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
            source.getPost().forEach(postCommand -> user.getPosts().add(requestBodyPostToPost.convert(postCommand)));
        }
        user.setLocation(requestBodyLocationToLocation.convert(source.getLocation()));
        user.setUserType(source.getUserType());

        return user;

    }
}
