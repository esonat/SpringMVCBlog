package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

import javassist.compiler.ast.IntConst;

public interface CommentRepository {
	Post 			getPostOfComment	(int commentID);
	List<Comment>	getAllComments();
	List<Comment> 	getAllCommentsByPostId(int postID);
	Comment 		getCommentById		(int commentID);	
	void			deleteComment		(int commentID);
	List<Comment> 	getPostComments		(int postID);
	Comment 		getPostCommentById	(int postID,int commentID);
	void			addPostComment		(Post post,Comment comment);
	List<Comment>   getChildComments(int commentID);
	void 			addChildComment		(Comment parentComment,Comment childComment);
	void			deleteChildComment	(Comment parentComment,int childCommentID);
}
