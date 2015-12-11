package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentRepository {
	List<Comment> 	getPostComments		(int postID);
	Comment 		getPostCommentById	(int postID,int commentID);
	void			addPostComment		(Post post,Comment comment);
	void			deletePostComment	(int commentID);
}
