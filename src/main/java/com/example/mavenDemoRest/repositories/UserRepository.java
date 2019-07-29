package com.example.mavenDemoRest.repositories;

import com.example.mavenDemoRest.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
