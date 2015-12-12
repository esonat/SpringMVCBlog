package com.sonat.blog.controller;

import java.util.ArrayList;
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
import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;
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

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.SecurityUtil;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
		
	@RequestMapping
	public String getAllPosts(Model model,
							  @ModelAttribute("comment") Comment comment){
		Map<Post,Map<Comment,List<Comment>>> postMap=new HashMap<Post, Map<Comment,List<Comment>>>();
		
		for(Post post:postService.getAll()){
			Map<Comment,List<Comment>> commentMap=new HashMap<Comment, List<Comment>>();
			List<Comment> postComments=new ArrayList<Comment>();
			postComments=commentService.getPostComments(post.getID());
			
			for(Comment c:postComments){
				if(!commentMap.containsKey(c)) 
					commentMap.put(c,commentService.getChildComments(post.getID(),c.getID()));
			}
			
			if(!postMap.containsKey(post)) postMap.put(post,commentMap);
		}
//		Map<String,List<Post>> postsMap=new HashMap<String, List<Post>>();
//		
//		for(User user:userService.getAll())
//		{
//			String 		username	=	user.getUsername();
//			List<Post> 	postList 	=	postService.getPostsByUsername(username);
//			
//			if(!postsMap.containsKey(username))postsMap.put(username,postList);
//		}				
//		
		model.addAttribute("postMap",postMap);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
//	    
		return "posts";
	}
	@RequestMapping("/{id}")
	public String getPostById(Model model,
							 @PathVariable("id")int id){
		Post post=postService.getPostById(id);
		
		if(post==null) return "redirect:/post";
		
		String username=post.getUser().getName();

		model.addAttribute("post",post);
		model.addAttribute("username",username);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "post";
	}		
	
	
	@RequestMapping("/user/{username}")
	public String getPostsByUsername(Model model,
									 @PathVariable("username")String username){
		
		List<Post> list=postService.getPostsByUsername(username); 
				
		model.addAttribute("post",list);
		model.addAttribute("user",username);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "user";
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
    public String getAddPostForm(@ModelAttribute("post") Post post,
    							 Model model,
    							 BindingResult result){
		
	    model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    return "addPost";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public String addPost(@ModelAttribute("post")@Valid Post post,
    					  Model model,
    					  BindingResult result){
        if(result.hasErrors()) return "addPost";
               	
    	postService.addPost(post);
        return "redirect:/post"; 
    }   
	
		
	@RequestMapping(value = "/{postId}/delete", method = RequestMethod.POST)
	public String deletePost(final RedirectAttributes redirectAttributes,
							 @PathVariable("postId") int postId){
		postService.deletePost(postId);
		return "redirect:/post";		
	}
	   
}
