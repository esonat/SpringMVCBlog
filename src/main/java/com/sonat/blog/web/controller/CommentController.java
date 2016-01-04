package com.sonat.blog.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.validator.CommentValidator;
import com.sonat.blog.exception.CommentNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.util.security.SecurityUtil;
import com.sonat.blog.util.security.SecurityUtilInterface;


@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CommentValidator commentValidator;
	@Autowired 
	private SecurityUtilInterface SecurityUtil;
	
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/add",method=RequestMethod.POST)
	public String addChildComment(@ModelAttribute("childComment")@Valid Comment childComment,
								  Model model,
								  BindingResult result,
								  @PathVariable("postId")int postId,
								  @PathVariable("commentId")int commentId,
								  @RequestParam("returnURL")String returnURL){
			
		commentValidator.validate(childComment, result);
		
		 if (result.hasErrors()){
			return "redirect:"+returnURL;
		 }
		 
		Comment parentComment=commentService.getCommentById(commentId);
		commentService.addChildComment(postId,parentComment,childComment);
		
		return "redirect:"+returnURL;
	}
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/add",method=RequestMethod.GET)
	public String addChildComment(Model model,
									@ModelAttribute("childComment")Comment comment,
									@PathVariable("postId")int postId,
									@PathVariable("commentId")int commentId){
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/"+postId+"/comment/"+commentId+"/comment/add");
		model.addAttribute("categories",categories);
		model.addAttribute("commentType","CHILD");
		model.addAttribute("postID",postId);
		model.addAttribute("commentID",commentId);
		
		return "addChildComment";
	}
	
	@RequestMapping(value="/post/{id}/comment/add",method=RequestMethod.GET)
	public String addPostComment(Model model,
								@PathVariable("id")int id,
								@ModelAttribute("postComment") Comment comment){
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/"+id+"/comment/add");
		model.addAttribute("categories",categories);
		model.addAttribute("postID",id);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		
		return "addComment";		
	}
	
	@RequestMapping(value="/post/{postId}/comment/add",method=RequestMethod.POST)
	public String addPostComment(Model model,
								@PathVariable("postId")int postId,
								@ModelAttribute("postComment") Comment postComment,
								@RequestParam("returnURL")String returnURL,
								BindingResult result,
								HttpServletRequest request,
								HttpServletResponse response){
		
		commentValidator.validate(postComment,result);
		if(result.hasErrors()){
			return "redirect:/post";		
		}
		
		commentService.addPostComment(postId,postComment);
		
		return "redirect:"+returnURL;		
	}
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/{childCommentId}/delete",
					method=RequestMethod.POST)
	public String deleteChildComment(@PathVariable("postId")int postId,	
									 @PathVariable("commentId")int commentId,
									 @PathVariable("childCommentId")int childCommentId
									 ,@RequestParam("returnURL")String returnURL){
		
		commentService.deleteChildComment(postId, commentId, childCommentId);
		return "redirect:"+returnURL;
	}	
	
//	/post/{id}/comment/{id}/comment/list 		:List all comments of a comment
//	/post/{id}/comment/{id}/comment/{id}		:Get comment of a comment
//	/post/{id}/comment/{id}/comment/add		:Add comment to a comment
//	/post/{id}/comment/{id}/comment/{id}/delete	:Delete comment of a comment(ADMIN)
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/delete",
					method=RequestMethod.POST)
	public String deletePostComment(@PathVariable("postId")		int postId,
									@PathVariable("commentId")	int commentId,
									@RequestParam("returnURL")String returnURL,
									HttpServletRequest request,
									HttpServletResponse response){
		
		commentService.deleteComment(postId,commentId);
		
		return "redirect:"+returnURL;		
	}
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/{childCommentId}")
	public String getChildComment(Model model,
								  @ModelAttribute("comment") Comment comment,
								  @PathVariable("postId")int postId,
								  @PathVariable("commentId")int commentId,
								  @PathVariable("childCommentId")int childCommentId){
		Comment childComment=commentService.getChildCommentById(postId, commentId, childCommentId);
		
		
		List<Category> categories=categoryService.getAllCategories();
		List<Comment> commentList=commentService.getChildCommentTree(childComment);
		
		model.addAttribute("returnURL","/post/"+postId+"/comment/"+commentId+"/comment/"+childCommentId);
		model.addAttribute("categories",categories);
		//model.addAttribute("commentType","CHILD");
	//	model.addAttribute("parentID",commentId);
		model.addAttribute("depth",childComment.getDepth());
		model.addAttribute("postID",postId);
		model.addAttribute("commentList",commentList);
		
		return "comment";		
	}
	@RequestMapping("/post/{postId}/comment/{commentId}")
	public String getPostComment(Model model,
								 @ModelAttribute("comment")Comment comment,
								 @PathVariable("postId")int postId,
								 @PathVariable("commentId")int commentId){
	
		Comment postComment	=	commentService.getPostCommentById(postId,commentId);
		if(postComment==null) return "redirect:/post";
		
		List<Comment> commentList	= commentService.getChildCommentTree(postComment);
		List<Category> categories	= categoryService.getAllCategories();
		
		model.addAttribute("returnURL","/post/"+postId+"/comment/"+commentId);
		model.addAttribute("postID",postId);
		model.addAttribute("categories",categories);
		model.addAttribute("commentList",commentList);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comment";		
	}
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/list")
	public String listChildComments(Model model,
									@PathVariable("postId")int postId,
									@PathVariable("commentId")int commentId){
		
		List<Comment> childComments=commentService.getChildComments(postId,commentId);
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("categories",categories);
		model.addAttribute("comments",childComments);
		return "comments";
	}
	
	@RequestMapping(value="/post/{postId}/comment/list",method=RequestMethod.GET)
	public String listPostComments(Model model,
								   @PathVariable("postId")int postId){
		
		List<Comment> 		postComments=commentService.getPostComments(postId);
		List<Category> categories=categoryService.getAllCategories();
		
		model.addAttribute("categories",categories);
		model.addAttribute("comments",postComments);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comments";		
	}
	
	@InitBinder("Comment")
	protected void initBinder(WebDataBinder binder) {
	    binder.setValidator(new CommentValidator());
	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, CommentNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidCommentId", exception.getCommentID());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("commentNotFound");
		 return mav;
	}	
	@ExceptionHandler(PostNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, PostNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidPostId", exception.getPostID());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("postNotFound");
		 return mav;
	}	
}
