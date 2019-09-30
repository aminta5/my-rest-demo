package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Page<Post>> findAll(Pageable pageable);
    Optional<Page<Post>> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
