package com.sonat.blog.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
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
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
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

private static final int VALID_POST_ID=8;
private static final int INVALID_POST_ID=1000;
	
	@Ignore
	public int getValidPostID(){
		return postDao.getAll()==null?null:postDao.getAll().get(0).getID();
	}
	@Ignore
	public int getPostCount(){
		return postDao.getAll().size();
	}

	@Ignore
	public void testAddPost(){
		user=userDao.getUserByUserName("engin");
		category=categoryDao.getCategoryByName("Java");
		post=new Post();
		post.setUser(user);
		post.setText("DENEME");
		postDao.addPost(post, category);
		
	}	
	@Test
	public void testValidGetPost(){
		Post post=postDao.get(getValidPostID());
		Assert.assertNotNull(post);
	}
	@Test
	public void testInvalidGetPost(){
		Post post=postDao.get(INVALID_POST_ID);
		Assert.assertNull(post);
	}
	
	@Test
	public void testValidDelete(){
		int validPostID=getValidPostID();
		int oldcount=getPostCount();
		
		Post post=postDao.get(validPostID);
		postDao.delete(post);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount, oldcount-1);
	}
	
	@Test(expected=DataAccessException.class)
	public void testInvalidDelete(){
		int oldcount=getPostCount();
		
		Post post=postDao.get(INVALID_POST_ID);
		postDao.delete(post);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount, oldcount);
	}	
	

	@Test
	public void testValidDeleteById(){
		int oldcount=getPostCount();
		postDao.deleteById(getValidPostID());
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount,oldcount-1);
	}
	
	@Test(expected=DataAccessException.class)
	public void testInvalidDeleteById(){
		int oldcount=getPostCount();
		postDao.deleteById(INVALID_POST_ID);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount,oldcount);
	}	
}
