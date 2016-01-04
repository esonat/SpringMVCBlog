package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.BlogUser;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.security.SecurityUtil;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<BlogUser> getAll() {
		return userDao.getAll();
	}
	@Override
	public void addUser(BlogUser user) {
		userDao.save(user);
	}
	@Override
	public BlogUser getUserByName(String name)
	throws UserNotFoundException{
		return userDao.getUserByName(name);
	}
	@Override
	public BlogUser getUserByUsername(String username)
	throws UserNotFoundException{
		return userDao.getUserByUserName(username);
	}

}
