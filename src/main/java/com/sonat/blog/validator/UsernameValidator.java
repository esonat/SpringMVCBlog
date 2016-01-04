package com.sonat.blog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.sonat.blog.domain.BlogUser;
import com.sonat.blog.service.UserService;

public class UsernameValidator implements ConstraintValidator<Username,String>{
	@Autowired
	private UserService userService;
	
	public void initialize(Username constraintAnnotation){
	}
	
	public boolean isValid(String value,ConstraintValidatorContext context){
		BlogUser user=null;
		try{
			user=userService.getUserByUsername(value);
		}catch(Exception e){
			return true;
		}
		if(user!=null){
			return false;
		}
		return true;
	}
}
