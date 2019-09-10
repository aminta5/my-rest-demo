package com.webfactory.mavenDemoRest.daoServices.DaoImplementations;

import com.webfactory.mavenDemoRest.daoServices.PostDaoService;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.requestBodies.RequestBodyPost;
import com.webfactory.mavenDemoRest.converters.RequestBodyLocationToLocation;
import com.webfactory.mavenDemoRest.converters.RequestBodyPostToPost;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.PostRepository;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostDaoImpl implements PostDaoService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RequestBodyLocationToLocation requestBodyLocationToLocation;
    private final RequestBodyPostToPost requestBodyPostToPost;

    //constructor
    public PostDaoImpl(PostRepository postRepository,
                       UserRepository userRepository,
                       RequestBodyLocationToLocation requestBodyLocationToLocation,
                       RequestBodyPostToPost requestBodyPostToPost) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.requestBodyLocationToLocation = requestBodyLocationToLocation;
        this.requestBodyPostToPost = requestBodyPostToPost;
    }

    @Override
    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        postRepository.findAll().forEach(posts::add);
        System.out.println(posts);
        return posts;
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post Not Found For This Id"));
    }

    @Override
    public List<Post> findPostByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title).orElseThrow(() -> new PostNotFoundException("No such title found"));
    }

    @Override
    public Post savePost(RequestBodyPost requestBodyPost) {
        User user = userRepository.findById(requestBodyPost.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Post newPost = requestBodyPostToPost.convert(requestBodyPost);
        if(newPost != null){
            newPost.setUser(user);
        }
        return postRepository.save(newPost);

    }

    @Override
    public Post updatePost(RequestBodyPost requestBodyPost, Long postId) {
        Optional<Post> postToUpdateOptional = postRepository.findById(postId);
        Post postToUpdate = postToUpdateOptional.orElseThrow(() -> new PostNotFoundException("Post Not found"));

        //update post
        if (requestBodyPost.getTitle() != null) {
            postToUpdate.setTitle(requestBodyPost.getTitle());
        }
        if (requestBodyPost.getDescription() != null) {
            postToUpdate.setDescription(requestBodyPost.getDescription());
        }

        //update of the location
        Location locationToUpdate = postToUpdate.getLocation();
        if (locationToUpdate == null) {
            locationToUpdate = new Location();
        }
        Location newLocation = requestBodyLocationToLocation.convert(requestBodyPost.getLocation());

        if (newLocation != null) {
            if (newLocation.getCity() != null) {
                locationToUpdate.setCity(newLocation.getCity());
            }
            if (newLocation.getCountry() != null) {
                locationToUpdate.setCountry(newLocation.getCountry());
            }
            if (newLocation.getLongitude() != null) {
                locationToUpdate.setLongitude(newLocation.getLongitude());
            }
            if (newLocation.getLatitude() != null) {
                locationToUpdate.setLatitude(newLocation.getLatitude());
            }
        }

        postToUpdate.setLocation(locationToUpdate);

        return postRepository.save(postToUpdate);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findPostsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return user.getPosts();
    }
}
