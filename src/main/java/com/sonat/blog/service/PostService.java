package com.sonat.blog.service;

import java.util.Date;
import java.util.List;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;

public interface PostService {
	List<Post> 		getAll();
	Post 			getPostById			(int ID);
	List<Post> 		getPostsByCategory	(int categoryID);
	List<Post> 		getPostsByUsername	(String username); 
	void 			addPost				(Post post,Category category);
	void 			deletePost			(int ID);
	List<Post>		getAllByDate		(Date dateFrom,Date dateTo); 
}
