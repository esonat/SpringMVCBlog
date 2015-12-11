package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

import javassist.compiler.ast.IntConst;

public interface CommentRepository {
	Comment 		getCommentById		(int commentID);	
	void			deleteComment		(int commentID);
	List<Comment> 	getPostComments		(int postID);
	Comment 		getPostCommentById	(int postID,int commentID);
	void			addPostComment		(Post post,Comment comment);
	List<Comment>   getChildComments	(int commentID);
	void 			addChildComment		(Comment comment,Comment childComment);
}
