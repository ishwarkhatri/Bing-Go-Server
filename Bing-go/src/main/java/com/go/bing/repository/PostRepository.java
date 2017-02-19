package com.go.bing.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.go.bing.model.Post;

@RepositoryRestResource(collectionResourceRel="posts", path="posts")
public interface PostRepository extends MongoRepository<Post, String> {
	
	/*** GET QUERIES ****/
	List<Post> findByCommunity(@Param("community") String community);
	
}
