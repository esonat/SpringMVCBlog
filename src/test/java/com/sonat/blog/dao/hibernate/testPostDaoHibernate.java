package com.sonat.blog.dao.hibernate;


import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class testPostDaoHibernate {

@Autowired
private UserService userService;
@Autowired
private CategoryService categoryService;
@Autowired
private PostDao postDao;

private User user;
private Category category;
private Post post;

	@Before
	public void init(){
		user=userService.getUserByUsername("engin");
		//category=categoryService.getCategoryByName("API");
		//post=new Post();
		//post.setText("DENEME");
	}
	@Test
	@Transactional
	public void testAddPost(){
		postDao.addPost(post, category);
	}	
}
