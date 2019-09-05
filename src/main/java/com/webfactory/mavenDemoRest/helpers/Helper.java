package com.webfactory.mavenDemoRest.helpers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

public class Helper {
    public static boolean isAdmin(User user) {
        return user.getUserType().equals(UserType.ROLE_ADMIN);
    }

    public static boolean isUser(User user) {
        return user.getUserType().equals(UserType.ROLE_USER);
    }

    public static boolean isSameUser(User user, Long id) {
        return user.getId().equals(id);
    }

    public static boolean isUserPost(Post post, User user) {
        return post.getUser().equals(user);
    }

}
