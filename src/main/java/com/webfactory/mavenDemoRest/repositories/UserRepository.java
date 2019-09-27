package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Post;
import com.webfactory.mavenDemoRest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<Page<User>> findAll(Pageable pageable);

    Optional<User> findByNicknameContainingIgnoreCase(String nickname);

    Optional<User> findByEmail(String email);
}
