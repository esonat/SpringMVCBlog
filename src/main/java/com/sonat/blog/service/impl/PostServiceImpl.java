package com.sonat.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.security.SecurityUtil;

@Service(value = "postService")
@Transactional
public class PostServiceImpl implements PostService{
	@Autowired
	private PostDao postDao;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityUtil securityUtil;
	
	public List<Post> getAll() {
		List<Post> list;
		try{
			list=postDao.getAll();
		}catch(Exception e){
			return null;
		}
		return list;
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
	
	@Transactional(rollbackFor=DataAccessException.class, readOnly=false, timeout=30, propagation=Propagation.SUPPORTS, isolation=Isolation.DEFAULT)
	public void addPost(Post post,Category category) {
		post.setCategory(category);
		post.setDate(new Date());
		post.setUser(securityUtil.getCurrentUser());	
	
		post.getUser().getPosts().add(post);
		
		postDao.save(post);
	}

	public void deletePost(int ID) {
		postDao.deleteById(ID);
	}
}
