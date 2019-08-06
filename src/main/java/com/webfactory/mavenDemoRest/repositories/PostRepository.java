package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
