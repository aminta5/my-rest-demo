package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.OauthClientDetails;
import org.springframework.data.repository.CrudRepository;

public interface ClientDetailsRepository extends CrudRepository<OauthClientDetails, String> {
}
