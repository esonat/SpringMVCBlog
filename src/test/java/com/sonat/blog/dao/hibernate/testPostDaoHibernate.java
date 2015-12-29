package com.sonat.blog.dao.hibernate;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.UserService;

import config.PostDaoTestConfig;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={PostDaoTestConfig.class})
public class testPostDaoHibernate {

@Autowired
private UserDao userDao;
@Autowired
private CategoryDao categoryDao;
@Autowired
private PostDao postDao;

private User user;
private Category category;
private Post post;

	@Test
	public void testAddPost(){
		user=userDao.getUserByUserName("engin");
		category=categoryDao.getCategoryByName("API");
		post=new Post();
		post.setText("DENEME");
		postDao.addPost(post, category);
	}	
}
