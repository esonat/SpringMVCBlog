package com.sonat.blog.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
//	List<User> userList=new ArrayList<User>(); 
	private int nextUserId;
	
	public UserRepositoryImpl(){
//		User user1=new User("user1");
//		user1.setID(1);
//		User user2=new User("user2");
//		user2.setID(2);
//		
//		userList.add(user1);
//		userList.add(user2);
//		
	}	
	public User getUserById(int ID) {
		User userById=null;
		for(User user:userList){
			if(ID==user.getID()) userById=user;
		}
		return userById;
	}
	public List<User> getAll(){
		return userList;
	}
	private synchronized int getNextUserId(){
		return nextUserId++;
	}
	
}
