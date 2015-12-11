package com.sonat.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostService postService;
	
	public Comment getCommentById(int commentID) {
		return commentRepository.getCommentById(commentID);
	}
	
	public List<Comment> getPostComments(int postID) {
		Post post=postService.getPostById(postID);
		if(post==null) return null;
		
		return commentRepository.getPostComments(postID);
	}

	public Comment getPostCommentById(int postID,int commentID) {
		Post post=postService.getPostById(postID);
		if(post==null) return null;
		
		return commentRepository.getPostCommentById(postID,commentID);
	}

	public void addPostComment(int postID, Comment comment) {
		Post post=postService.getPostById(postID);
		if(post==null) return;
		
		commentRepository.addPostComment(post,comment);
	}

	public void deletePostComment(int postID, int commentID) {
		Post post=postService.getPostById(postID);
		if(post==null) return;
		
		commentRepository.deletePostComment(commentID);
	}	
	public List<Comment>   getChildComments	(int postID,int commentID){
		Post post			=	postService.getPostById(postID);
		
		if(post==null) return null;
		
	}
	Comment 		getChildCommentById	(int postID,int commentID,int childCommentID);
	void 			addChildComment		(int postID,int commentID);
	void			deleteChildComment	(int postID,int commentID,int childCommentID);


}
