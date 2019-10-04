package com.webfactory.mavenDemoRest.services.serviceImplementations;

import com.webfactory.mavenDemoRest.repositories.LocationRepository;
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

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    private Logger logger = Logger.getLogger(getClass().getName());


    //constructor
    public PostServiceImpl(PostRepository postRepository,
                           UserRepository userRepository, LocationRepository locationRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).orElse(null);
    }

    @Override
    @Cacheable(value = "postsByIds", key = "#id")
    public Post getPostById(Long id) {
        logger.info("getPostById invoked");
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
    }

    @Override
    @Cacheable(value = "postsByTitle", key = "#title")
    public Page<Post> getPostByTitle(String title, Pageable pageable) {
        logger.info("getPostByTitle invoked");
        return postRepository.findByTitleContainingIgnoreCase(title, pageable).orElseThrow(() -> new PostNotFoundException(title));
    }

    @Override
    @Caching(put = {
            @CachePut(value = "postsByIds", key = "#result.id"),
            @CachePut(value = "postsByTitle", key = "#result.title")
    })
    public Post createPost(Post newPost) {
        if (newPost.getLocation() != null) {
            Optional<Location> locationOptional = locationRepository.findByCityContainingIgnoreCase(newPost.getLocation().getCity());
            if (locationOptional.isPresent()) {
                Location location = locationOptional.get();
                location.addPost(newPost);
                newPost.setLocation(location);
                locationRepository.save(location);
            }
        }
        return postRepository.save(newPost);
    }

    @Override
    @Caching(put = {
            @CachePut(value = "postsByIds", key = "#postId"),
            @CachePut(value = "postsByTitle", key = "#result.title")
    })
    public Post updatePost(Post postUpdateObject, Long postId) {
        logger.info("updatePost invoked");

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
        if (postUpdateObject.getLocation() != null) {
            Optional<Location> locationExist = locationRepository.findByCityContainingIgnoreCase(postUpdateObject.getLocation().getCity());
            Location location = locationExist.orElseGet(postUpdateObject::getLocation);
            location.addPost(postToUpdate);
            postToUpdate.setLocation(location);
        }

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
