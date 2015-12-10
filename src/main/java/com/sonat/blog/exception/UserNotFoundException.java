package com.sonat.blog.exception;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3211319429457536575L;
	
	private String username;
	
	public UserNotFoundException(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
}
