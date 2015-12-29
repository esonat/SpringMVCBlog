package com.sonat.blog.dao.hibernate;

import org.apache.taglibs.standard.lang.jstl.test.StaticFunctionTests;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;

import junit.framework.Assert;

import com.sonat.blog.domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
public class testCommentDaoHibernate {

	@Autowired
	private UserDao userDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private CommentDao commentDao;
	
	
	private User user;
	private Category category;
	private Post post;
		
	private static final int INVALID_COMMENT_ID=1000;
	
	@Ignore
	public Post getValidPost(){
		return postDao.getAll()==null?null:postDao.getAll().get(0);
	}
	@Ignore
	public int getValidPostCommentID(Post post){
		return commentDao.getPostComments(post.getID())==null
				?0:commentDao.getPostComments(post.getID()).get(0).getID();
	}
	@Ignore
	public int getPostCommentCount(Post post){
		return commentDao.getPostComments(post.getID())==null?
				0:commentDao.getPostComments(post.getID()).size();
	}
	@Ignore
	public 
	
	@Test
	public void testValidAddPostComment(){
		Post post=getValidPost();
		int oldcount= getPostCommentCount(post);
		
		Comment comment=new Comment();
		comment.setText("Test Comment");
				
		commentDao.addPostComment(post, comment);
		int newcount=getPostCommentCount(post);
		Assert.assertEquals(oldcount+1,newcount);
	}
	@Test(expected=RuntimeException.class)
	public void testAddNullPostComment(){
		Post post=null;
		
		Comment comment=new Comment();
		comment.setText("Test Comment");
				
		commentDao.addPostComment(post, comment);
	}	
	@Test(expected=DataAccessException.class)
	public void testAddInvalidPostComment(){
		Post post=getValidPost();
		
		Comment comment=new Comment();
		commentDao.addPostComment(post, comment);
	}	
	@Test
	public void testAddValidChildComment(){
		
	}
	
	
	
}