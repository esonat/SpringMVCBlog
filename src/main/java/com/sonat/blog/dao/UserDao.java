package com.sonat.blog.dao;

import com.sonat.blog.domain.User;

public interface UserDao {
	User findByUserName(String username);
}
