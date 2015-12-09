package com.sonat.blog.service;

import java.util.List;

import com.sonat.blog.domain.User;

public interface UserService {
	User getUserById(int ID);
	List<User> getAll();
	void addUser(User user);
}
