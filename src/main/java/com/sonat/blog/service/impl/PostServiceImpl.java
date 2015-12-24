package com.sonat.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;

@Service(value = "postService")
public class PostServiceImpl implements PostService{

	private PostDao postDao;
	private CategoryService categoryService;
	private UserService userService;
	
	public List<Post> getAll() {
		return postDao.getAll();
	}
	
	public List<Post> getAllByDate(Date dateFrom,Date dateTo) {
		return postDao.getAllByDate(dateFrom,dateTo);
	} 

	public Post getPostById(int ID)
	throws PostNotFoundException{
		return postDao.get(ID);
	}
	
	public List<Post> getPostsByCategory(int categoryID){
		categoryService.getCategoryById(categoryID);
		return postDao.getPostsByCategory(categoryID);
	}

	public List<Post> getPostsByUsername(String username) {
		userService.getUserByUsername(username);
		return postDao.getPostsByUsername(username);
	}

	public void addPost(Post post,Category category) {
		post.setCategory(category);
		postDao.save(post);
	}

	public void deletePost(int ID) {
		Post post=postDao.get(ID);
		postDao.delete(post);
	}
}
