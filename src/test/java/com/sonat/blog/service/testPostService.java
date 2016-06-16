package com.sonat.blog.service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.exception.UserNotFoundException;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
public class testPostService {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryDao categoryDao;

	private static final int VALID_POST_ID=3;
	private static final int INVALID_POST_ID=1000;
	private static final int POST_COUNT=3;
	private static final int VALID_CATEGORY_ID=1;
	private static final int VALID_CATEGORY_COUNT=3;
	private static final int INVALID_CATEGORY_ID=1000;
	private static final String VALID_USER_USERNAME="engin";
	private static final String INVALID_USER_USERNAME="invalidUser";

	Lock sequential = new ReentrantLock();

	@Before
	public void init(){
		sequential.lock();
	}
	@After
	public void destroy(){
		sequential.unlock();
	}

	@Test(expected=PostNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testDeleteInvalidPost(){
		postService.deletePost(INVALID_POST_ID);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll(){
		List<Post> list=postService.getAll();
		Assert.assertNotNull(list);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostByValidId(){
		Post post=postService.getPostById(VALID_POST_ID);
		Assert.assertNotNull(post);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostsByValidCategory(){
		List<Post> list=postService.getPostsByCategory(VALID_CATEGORY_ID);
		Assert.assertNotNull(list);
	}
	@Test(expected=CategoryNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostsByInvalidCategory(){
		List<Post> list=postService.getPostsByCategory(INVALID_CATEGORY_ID);
		Assert.assertNull(list);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostsByValidUsername(){
		List<Post> list=postService.getPostsByUsername(VALID_USER_USERNAME);
		Assert.assertNotNull(list);
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostsByInvalidUsername(){
		List<Post> list=postService.getPostsByUsername(INVALID_USER_USERNAME);
		Assert.assertNull(list);
	}

}
