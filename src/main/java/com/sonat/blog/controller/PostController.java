package com.sonat.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;

@Controller
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/post")
	public String listAll(Model model){
		Map<String,List<Post>> map=new HashMap<String, List<Post>>();
		
		for(User user:userService.getAll()){
			int userId		=	user.getID();
			String userName	=	user.getName();
			List<Post> userPosts=postService.getPostsByUserID(userId);
			
			if(!map.containsKey(userName))map.put(userName,userPosts);
		}		
		model.addAttribute("map",map);
		return "posts";
	}
	@RequestMapping("/post/{id}")
	public String getPostById(Model model,@PathVariable("id")int id){
		Post post=postService.getPostById(id);
		String userName=userService.getUserById(post.getUser().getID()).getName();
		
		model.addAttribute("post",post);
		model.addAttribute("user",userName);
		
		return "post";
	}	
	@RequestMapping("/user/{userId}")
	public String getPostByUserId(Model model,@PathVariable("userId")int userId){
		List<Post> list=postService.getPostsByUserID(userId); 
		String userName=userService.getUserById(userId).getName();
		
		model.addAttribute("post",list);
		model.addAttribute("user",userName);
		return "user";
	}

	@RequestMapping(value="/post/add", method=RequestMethod.GET)
    public String addPost(Model model){
		return "addPost";
	}
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST)
    public void addPost(@ModelAttribute("post") Post post, BindingResult result, Model model)
    {
        if( ! result.hasErrors() ){
             
        } 
    }   
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");
		return model;
	}
	
	
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;
	}
	
	@RequestMapping(value="/user/add",method=RequestMethod.GET)
	public String addUser(Model model){
		return "addUser";
	}
	
	@RequestMapping(value="/user/add", method=RequestMethod.POST)
    public void addPost(@ModelAttribute("user") User user, BindingResult result, Model model)
    {
        //if( ! result.hasErrors() ){
             userService.addUser(user);
        //} 
    }   
}
