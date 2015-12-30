package com.sonat.blog.service;

import java.util.List;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentService {
	void 			addChildComment		(int postID,Comment parentComment,Comment childComment);
	void			addPostComment		(int postID,Comment comment);
	void			deleteChildComment	(int postID,int commentID,int childCommentID);
	void			deleteComment		(int postID,int commentID);
	List<Comment>	getAllComments();
	List<Comment> 	getAllCommentsByPostId(int postID);
	Comment 		getChildCommentById	(int postID,int commentID,int childCommentID);
	List<Comment>   getChildComments	(int postID,int commentID);
	List<Comment>   getChildCommentsByDepth(int commentID,int depth);
	List<Comment> 	getChildCommentTree(Comment comment);
	Comment 		getCommentById		(int commentID);
	List<Comment>   getCommentsByDepth	(int postID,int depth);
	public List<Comment> getCommentTree(Post post);
	Comment 		getPostCommentById	(int postID,int commentID);
	List<Comment> 	getPostComments		(int postID);
	//Post 			getPostOfComment(int commentID);
}
