package com.sonat.blog.domain.repository;


import java.util.List;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;

public interface UserRepository {
	User getUserByName(String name);
	User getUserByUsername(String username);
	List<User> getAll(); 
	void addUser(User user);
}
