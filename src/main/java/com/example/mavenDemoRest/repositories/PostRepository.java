package com.example.mavenDemoRest.repositories;

import com.example.mavenDemoRest.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
