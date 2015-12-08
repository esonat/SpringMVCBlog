package com.sonat.blog.service;

import java.util.List;
import com.sonat.blog.domain.Post;

public interface PostService {
	List<Post> 	getAll();
	Post 		getPostById(int ID);
	List<Post> 	getPostsByUserID(int userID); 
	void 		addPost(Post post);
	void 		deletePost(int ID);
}
