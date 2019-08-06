package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
