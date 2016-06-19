package com.sonat.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping("/")
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
	@RequestMapping("/{id}")
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
}
