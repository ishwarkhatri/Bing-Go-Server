package com.go.bing.repository;

import java.util.List;

import com.go.bing.model.User;

public interface UserRepositoryCustom {

	public List<User> findAllRegisteredUsers();
}
