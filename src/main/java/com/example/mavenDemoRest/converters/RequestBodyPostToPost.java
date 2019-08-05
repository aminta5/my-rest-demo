package com.example.mavenDemoRest.converters;

import com.example.mavenDemoRest.requestBodies.RequestBodyPost;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestBodyPostToPost implements Converter<RequestBodyPost, Post> {
    private RequestBodyLocationToLocation requestBodyLocationToLocation;
    private RequestBodyUserToUser requestBodyUserToUser;


    //setter  for injection

    @Autowired
    public void setRequestBodyLocationToLocation(RequestBodyLocationToLocation requestBodyLocationToLocation) {
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
    }

    @Autowired
    public void setRequestBodyUserToUser(RequestBodyUserToUser requestBodyUserToUser) {
        this.requestBodyUserToUser = requestBodyUserToUser;
    }

    @Override
    public Post convert(RequestBodyPost source) {
        if(source == null){
            return null;
        }

        final Post post = new Post();
        post.setId(source.getId());
        post.setTitle(source.getTitle());
        post.setDescription(source.getDescription());

        if(source.getId() != null){
            User user = new User();
            user.setId(source.getUserId());
            post.setUser(user);
            user.getPosts().add(post);
        }
        post.setLocation(requestBodyLocationToLocation.convert(source.getLocation()));
        return post;
    }
}
