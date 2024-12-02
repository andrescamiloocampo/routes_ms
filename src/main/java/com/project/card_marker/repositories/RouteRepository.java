package com.project.card_marker.repositories;

import com.project.card_marker.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository extends MongoRepository<Route, String>{
}
