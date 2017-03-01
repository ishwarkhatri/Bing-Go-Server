package com.go.bing.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.go.bing.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {

}
