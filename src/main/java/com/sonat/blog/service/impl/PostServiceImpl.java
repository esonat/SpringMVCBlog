package com.sonat.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.security.SecurityUtil;
import com.sonat.blog.util.security.SecurityUtilInterface;

@Service(value = "postService")
public class PostServiceImpl implements PostService{
	@Autowired
	private PostDao postDao;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityUtilInterface securityUtil;

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

	public List<Post> getPostsByCategory(int categoryID)
	throws CategoryNotFoundException{
		categoryService.getCategoryById(categoryID);
		return postDao.getPostsByCategory(categoryID);
	}

	public List<Post> getPostsByUsername(String username)
	throws UserNotFoundException{
		userService.getUserByUsername(username);
		return postDao.getPostsByUsername(username);
	}

	//@Transactional(rollbackFor=DataAccessException.class, readOnly=false, timeout=30, propagation=Propagation.SUPPORTS, isolation=Isolation.DEFAULT)
	public void addPost(Post post,Category category) {
		postDao.addPost(post, category);
	}

	public void deletePost(int ID) {
		postDao.deleteById(ID);
	}
}
