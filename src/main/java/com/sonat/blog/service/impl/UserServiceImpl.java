package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.BlogUser;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public List<BlogUser> getAll() {
		return userDao.getAll();
	}
	public void addUser(BlogUser user) {
		userDao.addUser(user);
	}
	public BlogUser getUserByName(String name)
	throws UserNotFoundException{
		return userDao.getUserByName(name);
	}
	public BlogUser getUserByUsername(String username)
	throws UserNotFoundException{
		return userDao.getUserByUserName(username);
	}

}
