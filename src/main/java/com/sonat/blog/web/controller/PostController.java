package com.sonat.blog.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;

//import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.sonat.blog.domain.validator.PostValidator;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.util.security.SecurityUtil;
import com.sonat.blog.util.security.SecurityUtilInterface;
import com.sonat.blog.web.model.*;
import com.sonat.blog.web.validator.DateQueryValidator;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired 
	private SecurityUtilInterface SecurityUtil;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PostValidator postValidator;
		
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
    public String addPost(@ModelAttribute("post")@Valid Post post,
    					  Model model,
    					  @RequestParam(value="categoryName")String categoryName,
    					  @RequestParam("returnURL")String returnURL,
    					  BindingResult result,
    					  HttpServletRequest request,
    					  HttpServletResponse response,
    					  RedirectAttributes redir){
		
		postValidator.validate(post, result);
        
		if(result.hasErrors()) {
			return "redirect:"+returnURL;
		}
		else{
        	Category category=categoryService.getCategoryByName(categoryName);
         	postService.addPost(post,category);
         	return "redirect:/post"; 
        }
	}
	@RequestMapping(value = "/{postId}/delete", method = RequestMethod.POST)
	public String deletePost(final RedirectAttributes redirectAttributes, @PathVariable("postId") int postId,
			@RequestParam("returnURL") String returnURL) {

		postService.deletePost(postId);
		return "redirect:" + returnURL;
	}		
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView getAddPostForm(@ModelAttribute("post") Post post,
    							 Model model,
    							 BindingResult result,
    							 RedirectAttributes redir){
		ModelAndView mav=new ModelAndView();
		
		List<Category> categories=categoryService.getAllCategories();
		mav.addObject("returnURL","/post/add");
		mav.addObject("categories",categories);
		mav.addObject("loggedUser",SecurityUtil.getCurrentUsername());
		mav.setViewName("addPost");
	    
		return mav;
	}	
	
	@RequestMapping
	public String getAllPosts(Model model,
							@ModelAttribute("comment") Comment comment,
							@RequestParam(value="dateFrom",required=false)String dateFrom,
							@RequestParam(value="dateTo",required=false)  String dateTo,
							@RequestParam(value="dateQuery",required=false)DateQueryEnum dateQuery){

		Map<Post,List<Comment>> postsMap=new LinkedHashMap<Post,List<Comment>>();
		List<Post> postList=new ArrayList<Post>();
				
		boolean isDateParamsValid=DateQueryValidator.setDateValues(dateFrom, dateTo, dateQuery);
		if(!isDateParamsValid) 
			postList=postService.getAll();
		else
			postList=postService.getAllByDate(DateQueryValidator.from, DateQueryValidator.to);
		
		if(postList!=null){
			for(Post post:postList){
				if(!postsMap.containsKey(post))	postsMap.put(post,commentService.getCommentTree(post));
			}
		}
		
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post");
		model.addAttribute("categories",categories);	
		model.addAttribute("postsMap",postsMap);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "posts";
	}

	@RequestMapping("/{id}")
	public String getPostById(Model model,
							 @PathVariable("id")int id,
							 @ModelAttribute("comment") Comment comment){
		
		Post post=postService.getPostById(id);
		if(post==null) return "redirect:/post";
		List<Comment> commentList=commentService.getCommentTree(post);
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/"+id);
		model.addAttribute("categories",categories);	
		model.addAttribute("post",post);
		model.addAttribute("comments",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "post";
	}
	
	@RequestMapping(value="/category/{categoryId}")
	public String getPostsByCategory(Model model,
									 @ModelAttribute("comment") Comment comment,
									 @PathVariable("categoryId")int categoryId){
		
		List<Post> postList=postService.getPostsByCategory(categoryId);
		Map<Post,List<Comment>> postsMap=new LinkedHashMap<Post,List<Comment>>();
		
		if(postList!=null){
			for(Post post:postList){
				if(!postsMap.containsKey(post))	postsMap.put(post,commentService.getCommentTree(post));
			}
		}
		
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/category/"+categoryId);
		model.addAttribute("categories",categories);	
		model.addAttribute("postsMap",postsMap);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "posts";
	}   
	
		
	@RequestMapping("/user/{username}")
	public String getPostsByUsername(Model model,
									 @PathVariable("username")String username){
		
		List<Post> postList=postService.getPostsByUsername(username); 
		Map<Post,List<Comment>> postsMap=new LinkedHashMap<Post,List<Comment>>();
		
		if(postList!=null){
			for(Post post:postList){
				if(!postsMap.containsKey(post))	postsMap.put(post,commentService.getCommentTree(post));
			}
		}		
		
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/user/"+username);
		model.addAttribute("categories",categories);	
		model.addAttribute("postsMap",postsMap);
		model.addAttribute("user",username);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "user";
	}
	
	@ExceptionHandler(PostNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, PostNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidProductId", exception.getPostID());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("postNotFound");
		 return mav;
	}	
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ModelAndView handleCategoryError(HttpServletRequest req, CategoryNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidProductId", exception.getCategoryID());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("categoryNotFound");
		 return mav;
	}	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ModelAndView handleCategoryError(HttpServletRequest req, UserNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidUsername", exception.getUsername());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("userNotFound");
		 return mav;
	}
}