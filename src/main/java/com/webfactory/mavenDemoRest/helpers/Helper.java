package com.webfactory.mavenDemoRest.helpers;

import com.webfactory.mavenDemoRest.constants.UserType;
import com.webfactory.mavenDemoRest.model.User;

public class Helper {
    public static boolean isAdmin(User user){
        return user.getUserType().equals(UserType.ROLE_ADMIN) ? true : false;
    }
}
