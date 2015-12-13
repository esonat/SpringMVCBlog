package com.sonat.blog.service;

import java.util.List;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentService {
	Post 			getPostOfComment(int commentID);
	List<Comment>	getAllComments();
	List<Comment> 	getAllCommentsByPostId(int postID);
	Comment 		getCommentById		(int commentID);
	List<Comment> 	getPostComments		(int postID);
	Comment 		getPostCommentById	(int postID,int commentID);
	void			addPostComment		(int postID,Comment comment);
	void			deleteComment		(int postID,int commentID);
	List<Comment>   getChildComments	(int postID,int commentID);
	Comment 		getChildCommentById	(int postID,int commentID,int childCommentID);
	void 			addChildComment		(int postID,Comment parentComment,Comment childComment);
	void			deleteChildComment	(int postID,int commentID,int childCommentID);
}
