package com.webfactory.mavenDemoRest.converters;

import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestBodyPostToPost implements Converter<RequestBodyPost, Post> {
    private RequestBodyLocationToLocation requestBodyLocationToLocation;

    //setter injection
    @Autowired
    public void setRequestBodyLocationToLocation(RequestBodyLocationToLocation requestBodyLocationToLocation) {
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
    }

    @Override
    public Post convert(RequestBodyPost source) {
        if (source == null) {
            return null;
        }

        final Post post = new Post();
        //post.setId(source.getId());
        post.setTitle(source.getTitle());
        post.setDescription(source.getDescription());

        if (source.getUserId() != null) {
            User user = new User();
            user.setId(source.getUserId());
            post.setUser(user);
            user.getPosts().add(post);
        }
        post.setLocation(requestBodyLocationToLocation.convert(source.getLocation()));
        return post;
    }
}
