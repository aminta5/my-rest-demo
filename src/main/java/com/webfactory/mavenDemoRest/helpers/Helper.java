package com.webfactory.mavenDemoRest.helpers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;

public class Helper {

    public static boolean isUserPost(Post post, User user) {
        return post.getUser().equals(user);
    }

    public static boolean isSameUser(User user, Long id) {
        return user.getId().equals(id);
    }

    public static boolean isAdmin(User user) {
        return user.getUserType().equals(UserType.ROLE_ADMIN);
    }
}
