package com.sonat.blog.dao.hibernate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.BlogUser;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.exception.UserNotFoundException;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
public class testUserDaoHibernate {

	@Autowired
	private UserDao userDao;

	private static final int	VALID_USER_ID=1;
	private static final String VALID_NAME="admin";
	private static final String VALID_USERNAME="admin";
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

	@Test
	@Transactional
	@Rollback(true)
	public void testAddUser_validUser(){
		BlogUser user			= new BlogUser();
		user.setName("testUser");
		user.setUsername("testUser");
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
	public void testDeleteUser_validUser(){
		BlogUser user=userDao.get(VALID_USER_ID);
		int oldcount=userDao.getAll().size();
		userDao.delete(user);

		int newcount;
		if(userDao.getAll()==null) newcount=0;
		else
			newcount=userDao.getAll().size();

		Assert.assertEquals(oldcount-1,newcount);
	}

	@Test(expected=DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testDeleteUser_invalidUser(){
		BlogUser user=userDao.get(INVALID_USER_ID);

		int oldcount=userDao.getAll().size();
		userDao.delete(user);
		int newcount=userDao.getAll().size();

		Assert.assertEquals(oldcount,newcount);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetUserByName_validName(){
		BlogUser user=userDao.getUserByName(VALID_NAME);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getName(),VALID_NAME);
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetUserByName_invalidName(){
		BlogUser user=userDao.getUserByName(INVALID_NAME);
		Assert.assertNull(user);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetUserByUsername_validUsername(){
		BlogUser user=userDao.getUserByName(VALID_USERNAME);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getUsername(),VALID_USERNAME);
	}

	@Test(expected=UserNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetUserByName_invalidUsername(){
		BlogUser user=userDao.getUserByName(INVALID_USERNAME);
		Assert.assertNull(user);
	}
}
