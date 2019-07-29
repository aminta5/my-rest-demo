package com.example.mavenDemoRest;

import com.example.mavenDemoRest.constants.UserType;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.services.LocationService;
//import com.example.mavenDemoRest.services.PostService;
import com.example.mavenDemoRest.services.UserService;
import com.example.mavenDemoRest.util.IOUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataStore {
    // data structure for users
    private static List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    //data structure for posts
    private static List<Post> posts = new ArrayList<>();

    public static List<Post> getPosts() {
        return posts;
    }

    //data structure for Location
    private static List<Location> locations = new ArrayList<>();

    public static List<Location> getLocations() {
        return locations;
    }

    //methods
    private static void loadUsers(){
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "/Users/wf-filipnedelkovski/IdeaProjects/updateDemoApp1/mavenDemoRest/src/users.txt");
        for(String row : data){
            String[] values = row.split("#");
            //System.out.println(Arrays.toString(values));
            //System.out.println(values[0]);
            //Location location = locations.stream().filter(l -> l.getId() == Long.parseLong(values[6])).findFirst().get();
            //User user = UserService.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3], values[4], values[5], location, UserType.valueOf(values[7]));
            //users.add(user);
        }
        System.out.println("Users LOADED");
    }

    private static void loadLocations(){
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "/Users/wf-filipnedelkovski/IdeaProjects/updateDemoApp1/mavenDemoRest/src/location.txt");
        for(String row : data){

            String[] values = row.split("#");
            //System.out.println(Arrays.toString(values));
            //System.out.println(values[0]);
            long num = Long.parseLong(values[0]);
            float num1 = Float.parseFloat(values[3]);
            float num2 = Float.parseFloat(values[4]);

            Location location = LocationService.getInstance().createLocation(num/* Long.parseLong(values[0])*/, values[1], values[2], /*Float.parseFloat(values[3])*/num1, /*Float.parseFloat(values[4])*/num2);
            locations.add(location);
        }
        System.out.println("Locations LOADED");

    }

    private static void loadPosts(){
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "/Users/wf-filipnedelkovski/IdeaProjects/updateDemoApp1/mavenDemoRest/src/posts.txt");
        for(String row : data){
            String[] values = row.split("#");
            //System.out.println(Arrays.toString(values));
            //System.out.println(values[0]);

           // Location location = locations.stream().filter(l -> l.getId() == Long.parseLong(values[4])).findFirst().get();
            //User user = users.stream().filter(u -> u.getId() == Long.parseLong(values[3])).findFirst().get();
           // Post post = PostService.getInstance().createPost(Long.parseLong(values[0]), values[1], values[2], user, location);
           // posts.add(post);
        }
        System.out.println("Posts LOADED");


    }

    public static void removeUser(User user){
        users.remove(user);
    }

    public static User saveUser(User user){
        users.add(user);
        return user;
    }

    public static void loadData(){
        //loadLocations();
        //loadUsers();
        //loadPosts();
    }
}
