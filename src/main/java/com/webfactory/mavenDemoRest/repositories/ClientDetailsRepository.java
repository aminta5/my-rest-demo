package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.RegClientDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.oauth2.provider.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<RegClientDetails, String> {
}
