package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
	@Override
	public void addUser(User user) {
		userDao.save(user);		
	}
	@Override
	public User getUserByName(String name){
		return userDao.getUserByName(name);
	}
	@Override
	public User getUserByUsername(String username) 
	throws UserNotFoundException{
		return userDao.findByUserName(username);
	}
	
}
