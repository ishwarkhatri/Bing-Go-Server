package com.go.bing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Id @GeneratedValue String id;
	private String postMessage;
	@ManyToMany
	private User postedBy;
	private Date date;
	private String community;
	@OneToMany
	private List<Comment> comments;
	private Set<String> likes;
	private Set<String> disLikes;
	private boolean userLikes;
	private boolean userDisLikes;

	public Post() {
		this.comments = new ArrayList<>();
		this.likes = new HashSet<>();
		this.disLikes = new HashSet<>();
	}

	public boolean isUserLikes() {
		return userLikes;
	}

	public void setUserLikes(boolean userLikes) {
		this.userLikes = userLikes;
	}

	public boolean isUserDisLikes() {
		return userDisLikes;
	}

	public void setUserDisLikes(boolean userDisLikes) {
		this.userDisLikes = userDisLikes;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPostMessage() {
		return postMessage;
	}
	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
	public User getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public Set<String> getLikes() {
		return likes;
	}
	public void setLikes(Set<String> likes) {
		this.likes = likes;
	}
	public Set<String> getDisLikes() {
		return disLikes;
	}
	public void setDisLikes(Set<String> disLikes) {
		this.disLikes = disLikes;
	}
	
}
