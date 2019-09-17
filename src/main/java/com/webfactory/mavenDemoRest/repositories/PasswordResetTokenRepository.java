package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {
}
