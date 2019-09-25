package com.webfactory.mavenDemoRest.managers;

import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.services.UserService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
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

    public boolean authorizedUser(Authentication authentication, Long userId) {
        return userService.getUserByNickname(authentication.getName()).getId().equals(userId);
    }

    public boolean userCanBeUpdated(Authentication authentication, Long id) {
        return userService.getUserByNickname(authentication.getName()).getId().equals(id);
    }

    public boolean postCanBeUpdatedOrSeen(Authentication authentication, Long postId) {
        return postService.getPostById(postId).getUser().equals(userService.getUserByNickname(authentication.getName()));
    }
}
