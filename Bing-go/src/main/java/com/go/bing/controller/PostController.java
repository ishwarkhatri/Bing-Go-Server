package com.go.bing.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@ComponentScan
@RestController("/post")
public class PostController {
	private static Long POST_ID = new Long(1);
	private static String COMMUNITY_BING = "COMMUNITY_BING";
	@Autowired
	private PostRepository postRepository;

	@RequestMapping(path="/communityPosts/{userId}", method=RequestMethod.GET, consumes="application/json")
	public List<Post> getPostsForUserCommunity(@PathVariable("userId") User user) {
		return postRepository.findByCommunity(user.getCommunity());
	}

	@RequestMapping(path="/bingPosts", method=RequestMethod.GET, produces="application/json")
	public List<Post> getBingPosts() {
		return postRepository.findByCommunity(COMMUNITY_BING);
	}

	@RequestMapping(path="/addPostInUserCommunity/{userId}", method=RequestMethod.POST, consumes="application/json")
	public String addPostInUserCommunity(@PathVariable("userId") User user, @RequestBody String postMessage) {
		Post newPost = new Post();
		
		newPost.setPostId(POST_ID++);
		newPost.setPostMessage(postMessage);
		newPost.setComments(new ArrayList<>());
		newPost.setCommunity(user.getCommunity());
		newPost.setDate(Calendar.getInstance().getTime());
		newPost.setPostedBy(user.getUserId());

		postRepository.save(newPost);
		return "SUCCESS";
	}
	
	@RequestMapping(path="/addPostInBingCommunity/{userId}", method=RequestMethod.POST, consumes="application/json")
	public String addPostInBingComunity(@PathVariable("userId") User user, @RequestBody String postMessage) {
		Post newPost = new Post();
		
		newPost.setPostId(POST_ID++);
		newPost.setPostMessage(postMessage);
		newPost.setComments(new ArrayList<>());
		newPost.setCommunity(COMMUNITY_BING);
		newPost.setDate(Calendar.getInstance().getTime());
		newPost.setPostedBy(user.getUserId());

		postRepository.save(newPost);
		return "SUCCESS";
	}
	
	@RequestMapping(path="/addCommentToPost/{postId}/{userId}", method=RequestMethod.POST, consumes="application/json")
	public String addUserCommentToPost(@PathVariable("postId") Post post, @PathVariable("userId") User user, @RequestParam String comment) {
		post.getComments().add(new Comment(comment, user.getUserId(), Calendar.getInstance().getTime()));
		postRepository.save(post);
		return "SUCCESS";
	}
}
