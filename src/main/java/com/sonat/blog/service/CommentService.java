package com.sonat.blog.service;

import java.util.List;
import com.sonat.blog.domain.Comment;

public interface CommentService {
	List<Comment> 	getPostComments		(int postID);
	Comment 		getPostCommentById	(int postID,int commentID);
	void			addPostComment		(int postID,Comment comment);
	void			deletePostComment	(int postID,int commentID);
}
