package com.sonat.blog.dao.hibernate;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.exception.UserNotFoundException;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
/*@Transactional
@Rollback(true)*/
public class testUserDaoHibernate {

	@Autowired
	private PostDao postDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;

	private static final int	VALID_USER_ID=1;
	private static final String VALID_NAME="engin";
	private static final String VALID_USERNAME="engin";
	private static final int 	INVALID_USER_ID=1000;
	private static final String INVALID_NAME="invalidName";
	private static final String INVALID_USERNAME="invalidUsername";

	Lock sequential = new ReentrantLock();

	@Before
	public void init(){
		sequential.lock();
	}
	@After
	public void destroy(){
		sequential.unlock();
	}

	@SuppressWarnings("unchecked")
	@Test
	@Ignore
	/*@Transactional
	@Rollback(true)*/
	public void testAddUser_validUser(){
		User user			= new User();
		user.setName("testUser");
		user.setUsername("jackdaniels");
		user.setPassword("sonat");
		user.setEnabled(true);

		UserRole userRole	= new UserRole();
		userRole.setRole("ROLE_USER");

		user.getUserRole().add(userRole);
		userRole.setUser(user);

		int oldcount=userDao.getAll().size();
		userDao.save(user);
		int newcount=userDao.getAll().size();

		Assert.assertEquals(oldcount+1,newcount);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetUserByName_validName(){
		User user=userDao.getUserByName(VALID_NAME);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getName(),VALID_NAME);
	}
	@Test(expected=UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetUserByName_invalidName(){
		User user=userDao.getUserByName(INVALID_NAME);
		Assert.assertNull(user);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetUserByUsername_validUsername(){
		User user=userDao.getUserByName(VALID_USERNAME);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getUsername(),VALID_USERNAME);
	}
	@Test(expected=UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetUserByName_invalidUsername(){
		User user=userDao.getUserByName(INVALID_USERNAME);
		Assert.assertNull(user);
	}






}
