package com.sonat.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
			String username	=	user.getName();
			List<Post> userPosts=postService.getPostsByUsername(username);
			
			if(!map.containsKey(username))map.put(username,userPosts);
		}		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
		
	    model.addAttribute("map",map);
	    model.addAttribute("loggedUser",name);
		return "posts";
	}
	@RequestMapping("/post/{id}")
	public String getPostById(Model model,@PathVariable("id")int id){
		Post post=postService.getPostById(id);
		//String userName=userService.getUserById(post.getUser().getID()).getName();
		
		model.addAttribute("post",post);
		//model.addAttribute("user",userName);
		
		return "post";
	}	
	@RequestMapping("/post/user/{username}")
	public String getPostByUsername(Model model,@PathVariable("username")String username){
		List<Post> list=postService.getPostsByUsername(username); 
		//String userName=userService.getUserById(userId).getName();
		
		model.addAttribute("post",list);
		model.addAttribute("user",username);
		return "user";
	}

	@RequestMapping(value="/post/add", method=RequestMethod.GET)
    public String addPost(@ModelAttribute("post") Post post){
		return "addPost";
	}
	
	@RequestMapping(value="/post/add", method=RequestMethod.POST)
    public String addPost(@ModelAttribute("post")@Valid Post post, BindingResult result, Model model)
    {
        if( ! result.hasErrors() ){
        	//User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            postService.addPost(post);
            return "redirect:/post";
        } 
        return "addPost";
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
	public String addUser(@ModelAttribute("user") User user){
		return "addUser";
	}
	
	@RequestMapping(value="/user/add", method=RequestMethod.POST)
    public String addUser(@ModelAttribute("user") @Valid User userToBeAdded, BindingResult result, Model model)
    {
        if( ! result.hasErrors() ){
             userService.addUser(userToBeAdded);
             return "redirect:/post";
        }
        return "addUser";
    }   
}
