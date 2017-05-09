package com.go.bing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.go.bing.model.Apartment;

@RepositoryRestResource(collectionResourceRel="apartments", path="apartments")
public interface ApartmentRepository extends MongoRepository<Apartment, String>{

}
