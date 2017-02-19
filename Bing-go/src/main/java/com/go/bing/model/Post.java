package com.go.bing.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Long postId;
	private String postMessage;
	private String postedBy;
	private Date date;
	private List<Comment> comments;
	private String community;

	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getPostMessage() {
		return postMessage;
	}
	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
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
	
}
