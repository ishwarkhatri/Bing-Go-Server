package com.go.bing.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Community implements Serializable {

	private static final long serialVersionUID = 1L;
	private @Id @GeneratedValue String id;
	private String communityName;
	private String description;
	private Set<User> communityUsers;

	public Community() {
		this.communityUsers = new HashSet<>();
	}
	
	public Community(String name, String description) {
		this.communityName = name;
		this.description = description;
		this.communityUsers = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<User> getCommunityUsers() {
		return communityUsers;
	}
	public void setCommunityUsers(Set<User> communityUsers) {
		this.communityUsers = communityUsers;
	}
	
}
