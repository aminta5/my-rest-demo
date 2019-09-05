package com.webfactory.mavenDemoRest.permissions;

import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class CustomPermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object postId, Object permission) {
        System.out.println("postId = " + postId);
        System.out.println("permission = " + permission);
//        if (authentication != null && user instanceof User && post instanceof Post) {
//            return ((Post) post).getUser().equals(user);
//        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}
