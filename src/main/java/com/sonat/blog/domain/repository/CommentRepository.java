package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Comment;

public interface CommentRepository {
	Comment 		getCommentById(int commentID);
	List<Comment> 	getPostComments(int postID);
}
