package com.sonat.blog.util.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sonat.blog.domain.BlogUser;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.service.UserService;

@Component
public class SecurityUtil implements SecurityUtilInterface {
	@Autowired
	private UserService userService;
	
	public BlogUser getCurrentUser(){
		Authentication 	auth 		= SecurityContextHolder.getContext().getAuthentication();
	    String 			name 		= auth.getName(); 
		
	    BlogUser currentUser=null;
	    try{
			currentUser=userService.getUserByUsername(name);
		}catch(Exception e){
			return null;
		}
		return currentUser;
	}
	public String getCurrentUsername(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username		= auth.getName(); 
		
		return username;
	}
	public boolean isCurrentUserAdmin(){
		BlogUser currentUser=  getCurrentUser();
		if(currentUser	== null) return false;
	
		for(UserRole role:getCurrentUser().getUserRole()){
			if(role.getRole().equals("ROLE_ADMIN")) return true;
		}
		return false;
	}
}
