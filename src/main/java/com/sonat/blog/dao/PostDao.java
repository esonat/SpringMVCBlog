package com.sonat.blog.dao;

import java.util.Date;
import java.util.List;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;

public interface PostDao extends GenericDao<Post>  {
	void 		addPost(Post post,Category category);
	void 		deleteById(int ID);
	List<Post> 	getAllByDate(Date dateFrom, Date dateTo);
	List<Post> 	getPostsByCategory(int categoryID);
	List<Post> 	getPostsByUsername(String username);
	
	//List<Post> searchPosts(String keyword);
}
