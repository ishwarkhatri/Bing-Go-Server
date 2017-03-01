package com.go.bing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.go.bing.model.Event;

public interface EventRepository extends MongoRepository<Event, Long> {

}
