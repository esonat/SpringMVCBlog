package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface PostRepository {
	List<Post> 	getAll();
	Post 		getPostById(int ID);
	List<Post> 	getPostsByUsername(String username); 
	void 		addPost(Post post);
	void 		deletePost(int ID);
	void addPostComment(int postID, Comment comment);
	Comment getPostCommentById(int postID, int commentID); 
}