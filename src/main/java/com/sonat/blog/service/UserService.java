package com.sonat.blog.service;

import java.util.List;

import com.sonat.blog.domain.BlogUser;

public interface UserService {
	BlogUser getUserByName(String name);
	BlogUser getUserByUsername(String username);
	List<BlogUser> getAll();
	void addUser(BlogUser user);
}
