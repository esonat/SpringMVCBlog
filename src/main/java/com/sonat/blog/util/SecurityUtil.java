package com.sonat.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sonat.blog.domain.User;
import com.sonat.blog.service.UserService;

public class SecurityUtil {
	@Autowired
	private static UserService userService;
//	
//	public static User getLoggedUser(){
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	   
//	   // return userService.getUserByUsername(name);
//	    
//	}
//	public static String getLoggedUserRole(){
//		
//	}
}
