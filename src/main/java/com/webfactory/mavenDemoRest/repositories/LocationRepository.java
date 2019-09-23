package com.webfactory.mavenDemoRest.repositories;

import com.webfactory.mavenDemoRest.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByCityContainingIgnoreCase(String city);
}
