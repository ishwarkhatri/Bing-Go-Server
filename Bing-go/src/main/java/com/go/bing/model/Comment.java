package com.go.bing.model;

import java.util.Date;

public class Comment {

	private String commentMessage;
	private String commentedBy;
	private Date date;
	
	public Comment(String commentMessage, String commentedBy, Date date) {
		super();
		this.commentMessage = commentMessage;
		this.commentedBy = commentedBy;
		this.date = date;
	}

	public String getCommentMessage() {
		return commentMessage;
	}
	public void setCommentMessage(String commentMessage) {
		this.commentMessage = commentMessage;
	}
	public String getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(String commentBy) {
		this.commentedBy = commentBy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
