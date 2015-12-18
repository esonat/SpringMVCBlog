package com.sonat.blog.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.service.CommentService;

@Component
public class CommentValidator implements Validator{
	@Autowired
	private CommentService commentService;
	
	public boolean supports(Class clazz){
		return Comment.class.isAssignableFrom(clazz);
	}
	public void validate(Object target,Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"text","error.text","Comment text is required");
		Comment comment=(Comment)target;
		
		if(comment.getText().length()<5
		|| comment.getText().length()>100)
			errors.rejectValue("text","Size.Comment.text.validation");
	}
	

}
