package com.sonat.blog.dao;

import com.sonat.blog.domain.BlogUser;

public interface UserDao extends GenericDao<BlogUser>{
	void 	 addUser(BlogUser user);
	BlogUser getUserByUserName(String username);
	BlogUser getUserByName(String name); 
}
