package com.sonat.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.beans.factory.annotation.Autowired;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;

public class CommentTree {
	@Autowired
	PostService postService;
	@Autowired 
	CommentService commentService;

	private int commentID;
	private Comment root;
	
	public CommentTree(int commentID){
		this.commentID=commentID;
	}
	public Comment getRoot(){
		return commentService.getCommentById(commentID);
	}
	public List<Comment> getChildren(){
		return commentService.getChildComments(root.getPost().getID(),commentID);
	}
	public List<CommentTree> nextTree(){
		List<CommentTree> list=new ArrayList<CommentTree>();
		
	}
}
