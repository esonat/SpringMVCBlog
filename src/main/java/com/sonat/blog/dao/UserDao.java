package com.sonat.blog.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.BlogUser;

public interface UserDao extends GenericDao<BlogUser>{
	BlogUser getUserByUserName(String username);
	BlogUser getUserByName(String name); 
}
