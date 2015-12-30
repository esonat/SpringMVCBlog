package com.sonat.blog.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.event.PostCollectionRecreateEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.CategoryNotFoundException;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.UserService;
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

	private static final int VALID_POST_ID=1;
	private static final int POST_COUNT=3;
	private static final int VALID_CATEGORY_ID=1;
	private static final int VALID_CATEGORY_COUNT=3;
	private static final int INVALID_CATEGORY_ID=1000;

	Lock sequential = new ReentrantLock();

	@Before
	public void init(){
		sequential.lock();
	}
	@After
	public void destroy(){
		sequential.unlock();
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAll(){
		List<Post> list=postService.getAll();
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(),POST_COUNT);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostByValidId(){
		Post post=postService.getPostById(VALID_POST_ID);
		Assert.assertNotNull(post);
		Assert.assertEquals(post.getText(),"deneme");
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostsByValidCategory(){
		List<Post> list=postService.getPostsByCategory(VALID_CATEGORY_ID);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(),VALID_CATEGORY_COUNT);
	}
	@Test(expected=CategoryNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostsByInvalidCategory(){
		List<Post> list=postService.getPostsByCategory(INVALID_CATEGORY_ID);
		Assert.assertNull(list);
	}
}
