package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<List<Post>> findByTitleContainingIgnoreCase(String title);
}
