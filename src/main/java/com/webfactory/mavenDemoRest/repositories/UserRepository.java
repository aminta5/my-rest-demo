package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNicknameContainingIgnoreCase(String nickname);

    Optional<User> findByEmail(String email);
}
