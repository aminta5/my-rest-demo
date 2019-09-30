package com.webfactory.mavenDemoRest.managers;

import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;

@Service
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccessManager {
    private final UserService userService;
    private final PostService postService;

    //constructor
    public AccessManager(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    public boolean authorizedUser(String name, Long userId) {
        return userService.getUserByNickname(name).getId().equals(userId);
    }

    public boolean userCanBeUpdated(String name, Long id) {
        return userService.getUserByNickname(name).getId().equals(id);
    }

    public boolean postCanBeUpdatedOrSeen(String name, Long postId) {
        return postService.getPostById(postId).getUser().equals(userService.getUserByNickname(name));
    }
}
