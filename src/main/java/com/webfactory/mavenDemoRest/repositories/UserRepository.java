package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
