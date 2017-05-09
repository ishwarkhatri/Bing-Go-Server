package com.go.bing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.go.bing.model.ImageMetadata;

public interface ImageMetaRepository extends MongoRepository<ImageMetadata, String>{

}
