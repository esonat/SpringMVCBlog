package com.sonat.blog.service;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface PostService {
	List<Post> 		getAll();
	Post 			getPostById(int ID);
	List<Post> 		getPostsByUsername(String username); 
	void 			addPost(Post post);
	void 			deletePost(int ID);
	List<Comment> 	getPostComments(int postID);
	Comment 		getPostCommentById(int postID,int commentID);
	void			addPostComment(int postID,Comment comment);
	void			deletePostComment(int postID,int commentID);
}
