package com.sonat.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.service.UserService;


public class SecurityUtil {
	@Autowired
	private static UserService userService;
	
	public static User getCurrentUser(){
		Authentication 	auth 		= SecurityContextHolder.getContext().getAuthentication();
	    String 			name 		= auth.getName(); 
		
	    User currentUser=null;
	    try{
			currentUser=userService.getUserByName(name);
		}catch(Exception e){
			return null;
		}
		return currentUser;
	}
	public static String getCurrentUsername(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username		= auth.getName(); 
		
		return username;
	}
	public static boolean isCurrentUserAdmin(){
		User currentUser=  getCurrentUser();
		if(currentUser	== null) return false;
	
		for(UserRole role:getCurrentUser().getUserRole()){
			if(role.getRole().equals("ROLE_ADMIN")) return true;
		}
		return false;
	}
}
