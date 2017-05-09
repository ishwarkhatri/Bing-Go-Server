package com.go.bing.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Comment;
import com.go.bing.model.Post;
import com.go.bing.model.User;
import com.go.bing.repository.PostRepository;

@EnableAutoConfiguration
@ComponentScan
@RestController("/post")
public class PostController {
	private static String COMMUNITY_BING = "COMMUNITY_BING";
	@Autowired
	private PostRepository postRepository;

	@RequestMapping(path="/communityPosts/{userId}", method=RequestMethod.GET, consumes="application/json")
	public List<Post> getPostsForUserCommunity(@PathVariable("userId") User user) {
		List<Post> posts = postRepository.findByCommunity(user.getCommunity());

		for(Post p : posts) {
			if(p.getLikes().contains(user.getUserId()))
				p.setUserLikes(true);
			else if(p.getDisLikes().contains(user.getUserId()))
				p.setUserDisLikes(true);
		}
		return posts;
	}

	@RequestMapping(path="/bingPosts/{userId}", method=RequestMethod.GET, produces="application/json")
	public List<Post> getBingPosts(@PathVariable("userId") User user) {
		List<Post> posts = postRepository.findByCommunity(COMMUNITY_BING);
		for(Post p : posts) {
			if(p.getLikes().contains(user.getUserId()))
				p.setUserLikes(true);
			else if(p.getDisLikes().contains(user.getUserId()))
				p.setUserDisLikes(true);
		}
		return posts;
	}

	@RequestMapping(path="/addPostInUserCommunity/{userId}", method=RequestMethod.POST, consumes="application/json")
	public List<Post> addPostInUserCommunity(@PathVariable("userId") User user, @RequestBody String postMessage) {
		Post newPost = new Post();
		
		//newPost.setPostId(POST_ID++);
		newPost.setPostMessage(postMessage);
		newPost.setComments(new ArrayList<>());
		newPost.setCommunity(user.getCommunity());
		newPost.setDate(Calendar.getInstance().getTime());
		newPost.setPostedBy(user);

		postRepository.save(newPost);

		//Return the new list
		List<Post> posts = postRepository.findByCommunity(user.getCommunity());
		for(Post p : posts) {
			if(p.getLikes().contains(user.getUserId()))
				p.setUserLikes(true);
			else if(p.getDisLikes().contains(user.getUserId()))
				p.setUserDisLikes(true);
		}
		return posts;
	}
	
	@RequestMapping(path="/addPostInBingCommunity/{userId}", method=RequestMethod.POST, consumes="application/json")
	public List<Post> addPostInBingComunity(@PathVariable("userId") User user, @RequestBody String postMessage) {
		Post newPost = new Post();

		//newPost.setPostId(POST_ID++);
		newPost.setPostMessage(postMessage);
		newPost.setComments(new ArrayList<>());
		newPost.setCommunity(COMMUNITY_BING);
		newPost.setDate(Calendar.getInstance().getTime());
		newPost.setPostedBy(user);

		postRepository.save(newPost);

		//Return the new list
		List<Post> posts = postRepository.findByCommunity(COMMUNITY_BING);
		for(Post p : posts) {
			if(p.getLikes().contains(user.getUserId()))
				p.setUserLikes(true);
			else if(p.getDisLikes().contains(user.getUserId()))
				p.setUserDisLikes(true);
		}
		return posts;
	}
	
	@RequestMapping(path="/addUserLike/{id}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public void addUserLike(@PathVariable("id") String postId, @PathVariable("userId") User user) {
		Post post = postRepository.findById(postId);
		post.getLikes().add(user.getUserId());
		post.getDisLikes().remove(user.getUserId());
		postRepository.save(post);
	}
	
	@RequestMapping(path="/removeUserLike/{id}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public void removeUserLike(@PathVariable("id") String postId, @PathVariable("userId") User user) {
		Post post = postRepository.findById(postId);
		post.getLikes().remove(user.getUserId());
		postRepository.save(post);
	}
	
	@RequestMapping(path="/addUserDisLike/{id}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public void addUserDisLike(@PathVariable("id") String postId, @PathVariable("userId") User user) {
		Post post = postRepository.findById(postId);
		post.getDisLikes().add(user.getUserId());
		post.getLikes().remove(user.getUserId());
		postRepository.save(post);
	}
	
	@RequestMapping(path="/removeUserDisLike/{id}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public void removeUserDisLike(@PathVariable("id") Post post, @PathVariable("userId") User user) {
		post.getDisLikes().remove(user.getUserId());
		postRepository.save(post);
	}
	
	@RequestMapping(path="/addCommentToPost/{id}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public void addUserCommentToPost(@PathVariable("id") Post post, @PathVariable("userId") User user, @RequestBody String comment) {
		post.getComments().add(new Comment(comment, user, Calendar.getInstance().getTime()));
		postRepository.save(post);
	}
}
