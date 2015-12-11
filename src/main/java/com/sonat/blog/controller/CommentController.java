package com.sonat.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.deser.MapDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.service.PostService;
import com.sonat.blog.util.SecurityUtil;

@Controller
public class CommentController {
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/post/{id}/comment/list",method=RequestMethod.GET)
	public String listPostComments(@PathVariable("id")int id,Model model){
		List<Comment> 		postComments=postService.getPostComments(id);
		//Map<String,Comment>	userCommentMap=new HashMap<String, Comment>();
		
		model.addAttribute("comments",postComments);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comments";		
	}
	@RequestMapping("/post/{id}/comment/{id}")
	public String getPostComment(@PathVariable("postId")int postId,@PathVariable("commentId")int commentId,Model model){
		Comment postComment=postService.getPostCommentById(postId,commentId);
		String 	username=postComment.getPost().getUser().getUsername();
		
		model.addAttribute("postID",postId);
		model.addAttribute("username",username);
		model.addAttribute("comment",postComment);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
	    
		return "comment";		
	}
	@RequestMapping(value="/post/{id}/comment/add",method=RequestMethod.GET)
	public String addPostComment(@PathVariable("id")int id,Model model,@ModelAttribute("postComment") Comment comment){
		
		model.addAttribute("postID",id);
		model.addAttribute("loggedUser",SecurityUtil.getCurrentUsername());
		return "addComment";		
	}
	@RequestMapping(value="/post/{id}/comment/add",method=RequestMethod.POST)
	public String addPostComment(@PathVariable("id")int id,@ModelAttribute("postComment")@Valid Comment postComment,BindingResult bindingResult,Model model){
		if(bindingResult.hasErrors())	return "addComment";
		
		postService.addPostComment(id,postComment);
		return "redirect:/post";		
	}
	@RequestMapping(value="/post/{id}/comment/{id}/delete",method=RequestMethod.POST)
	public String deletePostComment(@PathVariable("postId")int postId,@PathVariable("commentId")int commentId){
		
		postService.deletePostComment(postId,commentId);
		return "addComment";		
	}	
}
