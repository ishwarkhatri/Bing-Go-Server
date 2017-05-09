package com.go.bing.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.go.bing.model.Community;

@RepositoryRestResource(collectionResourceRel="community", path="community")
public interface CommunityRepository extends MongoRepository<Community, String>{

	List<Community> findByCommunityName(@Param("communityName") String communityName);
}
