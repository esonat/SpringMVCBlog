package com.sonat.blog.controller;

import java.util.List;
import javax.validation.Valid;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.util.SecurityUtil;

import ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/post/{postId}/comment/list",method=RequestMethod.GET)
	public String listPostComments(Model model,
								   @PathVariable("postId")int postId){
		
		List<Comment> 		postComments=commentService.getPostComments(postId);
		
		model.addAttribute("comments",postComments);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comments";		
	}
	
	@RequestMapping("/post/{postId}/comment/{commentId}")
	public String getPostComment(Model model,
								 @PathVariable("postId")int postId,
								 @PathVariable("commentId")int commentId){
	
		Comment postComment	=	commentService.getPostCommentById(postId,commentId);
		
		if(postComment==null) return "redirect:/post";
		
		String 	username=postComment.getPost().getUser().getUsername();
		
		model.addAttribute("postID",postId);
		model.addAttribute("username",username);
		model.addAttribute("comment",postComment);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comment";		
	}
	
	@RequestMapping(value="/post/{id}/comment/add",method=RequestMethod.GET)
	public String addPostComment(Model model,
								@PathVariable("id")int id,
								@ModelAttribute("postComment") Comment comment){
		
		model.addAttribute("postID",id);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "addComment";		
	}
	
	@RequestMapping(value="/post/{postId}/comment/add",method=RequestMethod.POST)
	public String addPostComment(Model model,
								@PathVariable("postId")int postId,
								@ModelAttribute("postComment") @Valid Comment postComment,
								BindingResult bindingResult){
		
		if(bindingResult.hasErrors())	return "addComment";
		
		commentService.addPostComment(postId,postComment);
		return "redirect:/post";		
	}
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/delete",method=RequestMethod.POST)
	public String deletePostComment(@PathVariable("postId")		int postId,
									@PathVariable("commentId")	int commentId){
		
		commentService.deletePostComment(postId,commentId);
		return "redirect:/post";		
	}	
	
//	/post/{id}/comment/{id}/comment/list 		:List all comments of a comment
//	/post/{id}/comment/{id}/comment/{id}		:Get comment of a comment
//	/post/{id}/comment/{id}/comment/add		:Add comment to a comment
//	/post/{id}/comment/{id}/comment/{id}/delete	:Delete comment of a comment(ADMIN)
	
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/list")
	public String listChildComments(Model model,
									@PathVariable("postId")int postId,
									@PathVariable("commentId")int commentId){
		return "comments";
	}
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/{childCommentId}")
	public String getChildComment(Model model,
								  @PathVariable("postId")int postId,
								  @PathVariable("commentId")int commentId,
								  @PathVariable("childCommentId")int childCommentId){
		return "comments";		
	}
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/add",method=RequestMethod.GET)
	public String addChildComment(Model model,
									@PathVariable("postId")int postId,
									@PathVariable("commentId")int commentId){
		return "comments";
	}
	@RequestMapping(value="/post/{postId}/comment/{commentId}/comment/add",method=RequestMethod.POST)
	public String addChildComment(@ModelAttribute("childComment")@Valid Comment comment,
								  Model model,
								  BindingResult result,
								  @PathVariable("postId")int postId,
								  @PathVariable("commentId")int commentId){
		return "comments";
	}
	
	@RequestMapping(value="/post/{id}/comment/{id}/comment/{id}/delete",method=RequestMethod.POST)
	public String deleteChildComment(@PathVariable("postId")int postId,	
									 @PathVariable("commentId")int commentId,
									 @PathVariable("childCommentId")int childCommentId){
		
		return "redirect:/post";
	}
}
