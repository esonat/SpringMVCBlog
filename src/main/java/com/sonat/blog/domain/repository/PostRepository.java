package com.sonat.blog.domain.repository;

import java.util.List;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface PostRepository {
	List<Post> 	getAll();
	Post 		getPostById(int ID);
	List<Post> 		getPostsByCategory(int categoryID);
	List<Post> 	getPostsByUsername(String username); 
	void 		addPost(Post post,Category category);
	void 		deletePost(int ID);
}