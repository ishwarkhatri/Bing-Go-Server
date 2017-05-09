package com.go.bing.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	private String userId;
	@NotNull
	private String password;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String community;
	@NotNull
	private String emailId;
	@NotNull
	private USER_STATUS status;
	
	private String newCommunity;
	private String newCommunityDescription;
	
	public String getNewCommunity() {
		return newCommunity;
	}
	public void setNewCommunity(String newCommunity) {
		this.newCommunity = newCommunity;
	}
	public String getNewCommunityDescription() {
		return newCommunityDescription;
	}
	public void setNewCommunityDescription(String newCommunityDescription) {
		this.newCommunityDescription = newCommunityDescription;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public USER_STATUS getStatus() {
		return status;
	}
	public void setStatus(USER_STATUS status) {
		this.status = status;
	}

}
