package com.webfactory.mavenDemoRest.managers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.daoServices.UserDaoService;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccessManager {
    private final UserDaoService userService;
    private final PostDaoService postDaoService;

    //constructor
    public AccessManager(UserDaoService userService, PostDaoService postDaoService) {
        this.userService = userService;
        this.postDaoService = postDaoService;
    }

    public boolean authorizedUser(Authentication authentication, Long userId) {
        return userService.findUserByNickname(authentication.getName()).getId().equals(userId);
    }

    public boolean userCanBeUpdated(Authentication authentication, Long id) {
        return userService.findUserByNickname(authentication.getName()).getId().equals(id);
    }

    public boolean postCanBeUpdatedOrSeen(Authentication authentication, Long postId) {
        return postDaoService.findPostById(postId).getUser().equals(userService.findUserByNickname(authentication.getName()));
    }
}
