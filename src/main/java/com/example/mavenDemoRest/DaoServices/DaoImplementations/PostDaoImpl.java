package com.example.mavenDemoRest.DaoServices.DaoImplementations;

import com.example.mavenDemoRest.DaoServices.PostDaoService;
import com.example.mavenDemoRest.commands.PostCommand;
import com.example.mavenDemoRest.converters.LocationCommandToLocation;
import com.example.mavenDemoRest.converters.PostCommandToPost;
import com.example.mavenDemoRest.model.Location;
import com.example.mavenDemoRest.model.Post;
import com.example.mavenDemoRest.model.User;
import com.example.mavenDemoRest.repositories.PostRepository;
import com.example.mavenDemoRest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostDaoImpl implements PostDaoService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationCommandToLocation locationCommandToLocation;
    private final PostCommandToPost postCommandToPost;

    //constructor injection

    public PostDaoImpl(PostRepository postRepository, UserRepository userRepository, LocationCommandToLocation locationCommandToLocation, PostCommandToPost postCommandToPost) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.locationCommandToLocation = locationCommandToLocation;
        this.postCommandToPost = postCommandToPost;
    }

    @Override
    public List<Post> findAllPosts(){
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts :: add);
        System.out.println(posts);
        return posts;
    }

    @Override
    public Post findPostById(Long id){
       return postRepository.findById(id).orElse(null);
    }

    @Override
    public Location findPostLocation(Post post){
        return post.getLocation();
    }


    @Override
    public List<Post> findPostByTitle(String title){
        List<Post> posts = new ArrayList<>();
        findAllPosts().stream().filter(p -> p.getTitle().equalsIgnoreCase(title)).forEach(posts :: add);
        return posts;

    }

    @Override
    public Post savePost(PostCommand postCommand){
        Optional<User> userOptional = userRepository.findById(postCommand.getUserId());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        Post newPost = postCommandToPost.convert(postCommand);
        newPost.setUser(user);
        Post savedPost = postRepository.save(newPost);
        return savedPost;
    }

    @Override
    public Post updatePost(PostCommand postCommand, Long postId){
        Optional<Post> postToUpdateOptional = postRepository.findById(postId);
        Post postToUpdate = postToUpdateOptional.orElseThrow(() -> new RuntimeException("Post Not found"));

        //update post
        if(postCommand.getTitle() != null){
            postToUpdate.setTitle(postCommand.getTitle());
        }
        if(postCommand.getDescription() != null){
            postToUpdate.setDescription(postCommand.getDescription());
        }

        //update of the location
        Location locationToUpdate = postToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = locationCommandToLocation.convert(postCommand.getLocation());

        if(newLocation != null){
            if(newLocation.getCity() != null){
                locationToUpdate.setCity(newLocation.getCity());
            }
            if(newLocation.getCountry() != null){
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if(newLocation.getLongitude() != null && newLocation.getLongitude() != 0.0){
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if(newLocation.getLatitude() != null && newLocation.getLatitude() != 0.0){
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }


        postToUpdate.setLocation(locationToUpdate);

        Post savedPost = postRepository.save(postToUpdate);
        return savedPost;
    }

    @Override
    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
