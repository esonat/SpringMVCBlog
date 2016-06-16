package com.sonat.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.PostService;
import com.sonat.blog.service.UserService;
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

	public Post getPostById(int ID){
		if(postDao.get(ID)==null) throw new PostNotFoundException(ID);

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

	public void addPost(Post post,Category category) {
		postDao.addPost(post, category);
	}

	public void deletePost(int ID)
	throws PostNotFoundException{
		postDao.deleteById(ID);
	}
}
