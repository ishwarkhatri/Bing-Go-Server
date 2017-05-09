package com.go.bing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.go.bing.model.GalleryPost;

public interface GalleryRepository extends MongoRepository<GalleryPost, String> {

}
