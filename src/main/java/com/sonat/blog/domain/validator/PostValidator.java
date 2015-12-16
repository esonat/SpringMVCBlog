package com.sonat.blog.domain.validator;

import javax.swing.text.html.parser.TagElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sonat.blog.domain.Post;
import com.sonat.blog.service.PostService;

@Component
public class PostValidator implements Validator{
	@Autowired
	private PostService postService;
	
	public boolean supports(Class clazz){
		return Post.class.isAssignableFrom(clazz);
	}
	public void validate(Object target,Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"text","error.text","Text is required");
		Post post=(Post)target;
		if(post.getText().length()<5
		|| post.getText().length()>10000)
			errors.rejectValue("text","Size.Post.text.validation");
		
		for(Post item:postService.getAll()){
			if(post.getText().equals(item.getText()))
				errors.rejectValue("text", "Unique.Post.text.validation");
		}		
	}
}
