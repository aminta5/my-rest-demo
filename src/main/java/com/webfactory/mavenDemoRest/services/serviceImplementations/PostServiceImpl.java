package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.services.PostService;
import com.webfactory.mavenDemoRest.exceptions.PostNotFoundException;
import com.webfactory.mavenDemoRest.exceptions.UserNotFoundException;
import com.webfactory.mavenDemoRest.model.Location;
import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.repositories.PostRepository;
import com.webfactory.mavenDemoRest.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //constructor
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).orElse(null);
    }

    @Override
    @Cacheable(value = "postsByIds", key = "#id")
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
    }

    @Override
    @Cacheable(value = "postsByTitle", key = "#title")
    public Page<Post> getPostByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCase(title, pageable).orElseThrow(() -> new PostNotFoundException(title));
    }

    @Override
    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

    @Override
    @CachePut(value = "postsByIds", key = "#postId")
    public Post updatePost(Post postUpdateObject, Long postId) {
        Optional<Post> postToUpdateOptional = postRepository.findById(postId);
        Post postToUpdate = postToUpdateOptional.orElseThrow(() -> new PostNotFoundException(postId.toString()));

        //update post
        if (postUpdateObject.getTitle() != null) {
            postToUpdate.setTitle(postUpdateObject.getTitle());
        }
        if (postUpdateObject.getDescription() != null) {
            postToUpdate.setDescription(postUpdateObject.getDescription());
        }

        //update of the location
        Location locationToUpdate = postToUpdate.getLocation();
        if (locationToUpdate == null) {
            locationToUpdate = new Location();
        }
        Location newLocation = postUpdateObject.getLocation();

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
    @Caching(evict = {
            @CacheEvict(value = "postsByIds", key = "#id"),
    })
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "postsByUserIds", key = "#userId")
    public Page<Post> getPostsByUserId(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
        return new PageImpl<>(user.getPosts());
    }
}
