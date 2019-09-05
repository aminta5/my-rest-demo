package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import com.webfactory.mavenDemoRest.converters.RequestBodyLocationToLocation;
import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.PostRepository;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PostDaoImpl implements PostDaoService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RequestBodyLocationToLocation requestBodyLocationToLocation;
    private final RequestBodyPostToPost requestBodyPostToPost;

    //constructor injection

    public PostDaoImpl(PostRepository postRepository, UserRepository userRepository, RequestBodyLocationToLocation requestBodyLocationToLocation, RequestBodyPostToPost requestBodyPostToPost) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
        this.requestBodyPostToPost = requestBodyPostToPost;
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
    public Post savePost(RequestBodyPost requestBodyPost){
        Optional<User> userOptional = userRepository.findById(requestBodyPost.getUserId());
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        Post newPost = requestBodyPostToPost.convert(requestBodyPost);
        newPost.setUser(user);
        return postRepository.save(newPost);

    }

    @Override
    public Post updatePost(RequestBodyPost requestBodyPost, Long postId){
        Optional<Post> postToUpdateOptional = postRepository.findById(postId);
        Post postToUpdate = postToUpdateOptional.orElseThrow(() -> new RuntimeException("Post Not found"));

        //update post
        if(requestBodyPost.getTitle() != null){
            postToUpdate.setTitle(requestBodyPost.getTitle());
        }
        if(requestBodyPost.getDescription() != null){
            postToUpdate.setDescription(requestBodyPost.getDescription());
        }

        //update of the location
        Location locationToUpdate = postToUpdate.getLocation();
        if(locationToUpdate == null){
            locationToUpdate = new Location();
        }
        Location newLocation = requestBodyLocationToLocation.convert(requestBodyPost.getLocation());

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

    @Override
    public List<Post> findPostsByUserId(Long id) {
        List<Post> allPosts = new ArrayList<>();
        postRepository.findAll().forEach(allPosts :: add);
        List<Post> userPosts = new ArrayList<>();
        allPosts.stream().filter(p -> p.getUser().getId().equals(id)).forEach(userPosts :: add);

        return userPosts;
    }
}
