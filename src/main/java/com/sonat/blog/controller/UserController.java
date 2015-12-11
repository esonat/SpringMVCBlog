package com.sonat.blog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sonat.blog.domain.User;
import com.sonat.blog.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String addUser(Model model,@ModelAttribute("user") User user){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    model.addAttribute("loggedUser",name);
		return "addUser";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public String addUser(@ModelAttribute("user") @Valid User userToBeAdded, BindingResult result, Model model)
    {
        if( ! result.hasErrors() ){
             userService.addUser(userToBeAdded);
             return "redirect:/post";
        }
        return "addUser";
    }
	
}
