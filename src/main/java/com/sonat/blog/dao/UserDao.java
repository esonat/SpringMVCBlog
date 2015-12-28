package com.sonat.blog.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.User;

public interface UserDao extends GenericDao<User>{
	User getUserByUserName(String username);
	User getUserByName(String name); 
}
