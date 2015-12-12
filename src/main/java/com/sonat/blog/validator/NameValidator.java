package com.sonat.blog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.UserService;

public class NameValidator implements ConstraintValidator<Name,String>{
	@Autowired
	private UserService userService;
	
	public void initialize(Name constraintAnnotation){
	}
	
	public boolean isValid(String value,ConstraintValidatorContext context){
		User user=null;
		try{
			user=userService.getUserByName(value);
		}catch(Exception e){
			return true;
		}
		if(user!=null){
			return false;
		}
		return true;
	}
}