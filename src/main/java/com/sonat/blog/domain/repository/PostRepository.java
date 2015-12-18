package com.sonat.blog.domain.repository;

import java.util.List;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;

public interface PostRepository {
	void 		addPost(Post post,Category category);
	void 		deletePost(int ID);
	List<Post> 	getAll();
	Post 		getPostById(int ID); 
	List<Post> 		getPostsByCategory(int categoryID);
	List<Post> 	getPostsByUsername(String username);
}