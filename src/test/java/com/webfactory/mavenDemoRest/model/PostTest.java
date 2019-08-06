package com.webfactory.mavenDemoRest.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostTest {

    Post post;

    @Before
    public void setUp(){
        post = new Post();
    }

    @Test
    public void getTitle() {
        String title = "Sunshine";
        post.setTitle(title);
        assertEquals(title, post.getTitle());
    }

    @Test
    public void getDescription() {
        String description = "At vero eos et accusamus et iusto odio dignissimos ducimus";
        post.setDescription(description);
        assertEquals(description, post.getDescription());
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(1L);
        post.setUser(user);
        assertEquals(user.getId(), post.getUser().getId());
    }

    @Test
    public void getLocation() {
        Location location = new Location();
        location.setId(1L);
        post.setLocation(location);
        assertEquals(location.getId(), post.getLocation().getId());
    }
}