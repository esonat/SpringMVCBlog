package com.sonat.blog.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.User;

public interface UserDao extends GenericDao<User>,UserDetailsService{
	User findByUserName(String username);
	User findUserByName(String name); 
}
