package com.sonat.blog.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.event.PostCollectionRecreateEvent;
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
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.service.CategoryService;
import com.sonat.blog.service.UserService;
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

private static final int 	VALID_POST_ID=8;
private static final int 	INVALID_POST_ID=1000;
private static final int 	VALID_CATEGORY_ID=1;
private static final int 	INVALID_CATEGORY_ID=1000;
private static final String VALID_CATEGORY_NAME	="java";
private static final String VALID_USERNAME		="engin";
private static final String INVALID_USERNAME	="user";


	@Ignore
	public int getValidPostID(){
		return getPostCount()==0?0:postDao.getAll().get(0).getID();
	}
	@Ignore
	public int getPostCount(){
		return postDao.getAll().size();
	}
	@Ignore
	public Date getValidDateFrom(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2015,1,1,10,0,0);
		return calendar.getTime();
	}
	@Ignore
	public Date getValidDateTo(){
		return new Date();
	}
	@Ignore
	public Date getInvalidDateFrom(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2018,1,1,10,0,0);
		return calendar.getTime();
	}
	@Ignore
	public Date getInvalidDateTo(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2019,1,1,10,0,0);
		return calendar.getTime();
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
		if(getValidPostID()==0) return;
		
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
		if(getValidPostID()==0) return;
		
		int validPostID=getValidPostID();
		int oldcount=getPostCount();
		
		Post post=postDao.get(validPostID);
		postDao.delete(post);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount, oldcount-1);
	}
	
	@Test(expected=DataAccessException.class)
	public void testInvalidDelete(){
		if(getPostCount()==0) return;
		
		int oldcount=getPostCount();
		
		Post post=postDao.get(INVALID_POST_ID);
		postDao.delete(post);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount, oldcount);
	}	
	

	@Test
	public void testValidDeleteById(){
		if(getPostCount()==0) return;
		
		int oldcount=getPostCount();
		postDao.deleteById(getValidPostID());
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount,oldcount-1);
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvalidDeleteById(){
		if(getPostCount()==0) return;
		
		int oldcount=getPostCount();
		postDao.deleteById(INVALID_POST_ID);
		
		int newcount=getPostCount();
		Assert.assertEquals(newcount,oldcount);
	}	
	
	@Test
	public void testValidGetAllByDate(){
		List<Post> list=postDao.getAllByDate(getValidDateFrom(),
											 getValidDateTo());
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}
	
	@Test
	public void testInvalidGetAllByDate(){
		List<Post> list=postDao.getAllByDate(getInvalidDateFrom(),
											 getInvalidDateTo());
		Assert.assertNull(list);
	}
	@Test
	public void testValidGetPostsByCategory(){
		List<Post> list=postDao.getPostsByCategory(VALID_CATEGORY_ID);
		String categoryName=categoryDao.get(VALID_CATEGORY_ID).getName();
		
		for(Post item:list)
			Assert.assertEquals(categoryName,item.getCategory().getName());
		
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}
	@Test
	public void testInvalidGetPostsByCategory(){
		List<Post> list=postDao.getPostsByCategory(INVALID_CATEGORY_ID);
		
		Assert.assertNull(list);
	}
	
	@Test
	public void testValidGetPostsByUsername(){
		List<Post> list=postDao.getPostsByUsername(VALID_USERNAME);
		
		for(Post item:list)
			Assert.assertEquals(VALID_USERNAME,item.getUser().getUsername());
		
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}
	@Test
	public void testInvalidGetPostsByUsername(){
		List<Post> list=postDao.getPostsByUsername(INVALID_USERNAME);
		
		Assert.assertNull(list);
	}	
	
	
}
