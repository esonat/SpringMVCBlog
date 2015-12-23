package com.sonat.blog.dao;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.User;

public interface UserDao extends GenericDao<Category> {
	User findByUserName(String username);
	User getUserByName(String name); 
}
