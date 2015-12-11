package com.sonat.blog.util;

import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.beans.factory.annotation.Autowired;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;

public class CommentTree {
	@Autowired
	PostService postService;
	@Autowired 
	CommentService commentService;

	private int postID;
	private Map<Comment,List<Comment>> commentMap;
	
	public CommentTree(int postID){
		this.postID=postID;
	}
	
//	public Map<Comment,List<Comment>> formTree(){
//		List<Comment> rootComments=commentService.getPostComments(this.postID);
//		for (Comment comment : rootComments) {
//			
//		}
//	}
	
	
}
