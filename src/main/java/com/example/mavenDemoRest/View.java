package com.example.mavenDemoRest;

import com.example.mavenDemoRest.model.Post;

public class View {
    public static void displayPosts(Post post){
        System.out.println(post.getUser().getFirstName() + " " + post.getUser().getLastName() );
        System.out.println(post.getDescription());
    }
}
