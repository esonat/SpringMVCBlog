package com.sonat.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.QueryHint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.omg.CORBA.Request;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	public String admin(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	
		model.addAttribute("loggedUser",name);
		return "admin";
	}
	
	@RequestMapping("/post")
	public String listAll(Model model){
		Map<String,List<Post>> postsMap=new HashMap<String, List<Post>>();
		
		for(User user:userService.getAll()){
			String 		username	=	user.getUsername();
			List<Post> 	postList 	=	postService.getPostsByUsername(username);
			
			if(!postsMap.containsKey(username))postsMap.put(username,postList);
		}				
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
		
	    model.addAttribute("postsMap",	postsMap);
	    model.addAttribute("loggedUser",name);
	    
		return "posts";
	}
	@RequestMapping("/post/{id}")
	public String getPostById(Model model,@PathVariable("id")int id){
		Post post=postService.getPostById(id);
		
		if(post==null) return "redirect:/post";
		
		String username=post.getUser().getName();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
		
		model.addAttribute("post",post);
		model.addAttribute("username",username);
		model.addAttribute("loggedUser",name);
		
		return "post";
	}		
	
	@RequestMapping(value = "/post/{id}/delete", method = RequestMethod.POST)
	public String deletePost(@PathVariable("id") int id, final RedirectAttributes redirectAttributes) {
		postService.deletePost(id);
		return "redirect:/post";		
	}
	
	@RequestMapping("/post/user/{username}")
	public String getPostByUsername(Model model,@PathVariable("username")String username){
		List<Post> list=postService.getPostsByUsername(username); 
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
		
		model.addAttribute("post",list);
		model.addAttribute("user",username);
		model.addAttribute("loggedUser",name);
		return "user";
	}

	@RequestMapping(value="/post/add", method=RequestMethod.GET)
    public String addPost(Model model,@ModelAttribute("post") Post post){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    model.addAttribute("loggedUser",name);
		
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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
		model.setViewName("login");
		model.addObject("loggedUser",name);
		
		return model;
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
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
	public String addUser(Model model,@ModelAttribute("user") User user){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    model.addAttribute("loggedUser",name);
	    
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
