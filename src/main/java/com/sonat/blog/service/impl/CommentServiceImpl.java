package com.sonat.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.domain.repository.impl.CommentRepositoryImpl;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	//	@Autowired
//	private PostService postService;
//	
	public Post getPostOfComment(int commentID){
		return commentRepository.getPostOfComment(commentID);
	}
	public Comment getCommentById(int commentID) {
		return commentRepository.getCommentById(commentID);
	}
	
	public List<Comment> getAllCommentsByPostId(int postID){
		return commentRepository.getAllCommentsByPostId(postID);
	}
	
	public List<Comment> getPostComments(int postID) {
//		Post post=postService.getPostById(postID);
//		if(post==null) return null;
//		
		return commentRepository.getPostComments(postID);
	}

	public Comment getPostCommentById(int postID,int commentID) {
//		Post post=postService.getPostById(postID);
//		if(post==null) return null;
//		
		return commentRepository.getPostCommentById(postID,commentID);
	}

	public void addPostComment(int postID, Comment comment) {
//		Post post=postService.getPostById(postID);
//		if(post==null) return;
		Post post=postRepository.getPostById(postID);
		commentRepository.addPostComment(post,comment);
	}

	public void deleteComment(int postID, int commentID) {
//		Post post=postService.getPostById(postID);
//		if(post==null) return;
//		
		commentRepository.deleteComment(commentID);
	}	
	public List<Comment>   getChildComments	(int postID,int commentID){
//		Post post			=	postService.getPostById(postID);
//		if(post==null) return null;
//		
		return commentRepository.getChildComments(commentID);		
	}
	public Comment getChildCommentById(int postID,int commentID,int childCommentID){
//		Post post			=	postService.getPostById(postID);
//		if(post==null) return null;
//		
		return commentRepository.getCommentById(childCommentID);
	}
	public void addChildComment(int postID,Comment parentComment,Comment childComment){
//		Post post			=	postService.getPostById(postID);
//		if(post==null) return;
		
		commentRepository.addChildComment(parentComment,childComment);
	}
	public void deleteChildComment(int postID,int commentID,int childCommentID){
//		Post post			=	postService.getPostById(postID);
//		if(post==null) return;
		Comment parentComment=getCommentById(commentID);
		commentRepository.deleteChildComment(parentComment,childCommentID);
	}

	public List<Comment> getAllComments(){
		CommentRepository commentRepository=new CommentRepositoryImpl();
		return commentRepository.getAllComments();
	}
}
