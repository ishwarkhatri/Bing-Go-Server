package com.go.bing.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;

@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long commentId;
	private String commentMessage;
	@ManyToOne
	private User commentedBy;
	private Date date;
	
	@ManyToOne
	private Post post;

	public Comment(String commentMessage, User commentedBy, Date date) {
		super();
		this.commentMessage = commentMessage;
		this.commentedBy = commentedBy;
		this.date = date;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getCommentMessage() {
		return commentMessage;
	}
	public void setCommentMessage(String commentMessage) {
		this.commentMessage = commentMessage;
	}
	public User getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(User commentBy) {
		this.commentedBy = commentBy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
