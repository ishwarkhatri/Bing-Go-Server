package com.go.bing.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.go.bing.model.USER_STATUS;
import com.go.bing.model.User;

@RepositoryRestResource(collectionResourceRel="users", path="users")
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

	/*** GET QUERIES ****/
	List<User> findByUserId(@Param("userId") String userId);
	List<User> findByFirstName(@Param("firstName") String firstName);
	List<User> findByLastName(@Param("lastName") String lastName);
	List<User> findByEmailId(@Param("emailId") String emailId);
	List<User> findByStatus(@Param("status") USER_STATUS status);
	

	/*** UPDATE QUERIES ***/
	
	
}
