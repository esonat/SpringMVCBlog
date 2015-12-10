package com.sonat.blog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sonat.blog.domain.User;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.UserService;

public class UsernameValidator implements ConstraintValidator<Username,String>{
	@Autowired
	private UserService userService;
	
	public void initialize(Username constraintAnnotation){
	}
	
	public boolean isValid(String value,ConstraintValidatorContext context){
		User user;
		try{
			user=userService.getUserByUsername(value);
		}catch(UserNotFoundException e){
			return true;
		}
		if(user!=null){
			return false;
		}
		return true;
	}
}
