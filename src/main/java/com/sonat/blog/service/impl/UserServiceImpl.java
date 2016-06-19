package com.sonat.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.UserRepository;
import com.sonat.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	public User getUserById(int ID) {
		return userRepository.getUserById(ID);
	}	
	public List<User> getAll(){
		return userRepository.getAll();
	}	
}
