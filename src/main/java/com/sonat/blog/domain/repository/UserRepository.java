package com.sonat.blog.domain.repository;


import java.util.List;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;

public interface UserRepository {
	void addUser(User user);
	List<User> getAll();
	User getUserByName(String name); 
	User getUserByUsername(String username);
}
