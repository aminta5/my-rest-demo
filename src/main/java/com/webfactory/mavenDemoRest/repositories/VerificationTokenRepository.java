package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.User;
import com.webfactory.mavenDemoRest.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(User user);
}
