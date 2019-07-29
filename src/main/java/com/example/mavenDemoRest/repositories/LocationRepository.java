package com.example.mavenDemoRest.repositories;

import com.example.mavenDemoRest.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
