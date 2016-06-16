package com.sonat.blog.domain.repository;


import com.sonat.blog.domain.BlogUser;

public interface UserRepository {
	BlogUser getUserByName(String name); 
	BlogUser getUserByUsername(String username);
}
