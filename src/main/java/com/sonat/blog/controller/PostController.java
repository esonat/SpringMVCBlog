package com.sonat.blog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.CommentStruct;
import com.sonat.blog.util.CommentTree;
import com.sonat.blog.util.SecurityUtil;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentTree commentTree;
		
	@RequestMapping
	public String getAllPosts(Model model,
							  @ModelAttribute("comment") Comment comment){
		
		List<Post> postList=postService.getAll();
		List<CommentStruct> commentList=commentTree.getCommentList();
		
		model.addAttribute("postList",postList);
		model.addAttribute("commentList",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
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
	
	@RequestMapping(value="/category/{categoryId}")
	public String getPostsByCategory(Model model,
									 @ModelAttribute("comment") Comment comment,
									 @PathVariable("categoryId")int categoryId){
		List<Post> postList=postService.getPostsByCategory(categoryId); 
		//if(postList==null) return "redirect:/post";
		
		List<CommentStruct> commentList=commentTree.getCommentList();
		
		model.addAttribute("postList",postList);
		model.addAttribute("commentList",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "posts";
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
		
		//Map<Integer,String> categories=new HashMap<Integer,String>();
		List<String> categories=new ArrayList<String>();
		
		for(Category category:categoryService.getAllCategories()){
			categories.add(category.getName());
		}
		
		model.addAttribute("categories",categories);
	    model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    return "addPost";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public String addPost(@ModelAttribute("post")Post post,
    					  Model model,
    					  @RequestParam(value="categoryName")String categoryName,
    					  BindingResult result){
        if(result.hasErrors()) return "addPost";
               	
        Category category=categoryService.getCategoryByName(categoryName);
    	postService.addPost(post,category);
        return "redirect:/post"; 
    }   
	
		
	@RequestMapping(value = "/{postId}/delete", method = RequestMethod.POST)
	public String deletePost(final RedirectAttributes redirectAttributes,
							 @PathVariable("postId") int postId){
		postService.deletePost(postId);
		return "redirect:/post";		
	}
	   
}
