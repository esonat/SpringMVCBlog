package com.sonat.blog.dao.hibernate;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import junit.framework.Assert;
import net.sf.ehcache.terracotta.TerracottaBootstrapCacheLoader;

import com.sonat.blog.domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional  
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
	private static int totalcount=0;
	
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
	public int getCommentCount(){
		if(commentDao.getAll()==null) return 0;
		return commentDao.getAll().size();
	}
	@Ignore
	public Comment getValidPostComment(Post post){
		if(post==null) return null;
		if(commentDao.getPostComments(post.getID())==null) return null;
		
		return commentDao.getPostComments(post.getID()).get(0);
	}
	@Ignore
	public Comment getValidComment(){
		if(commentDao.getAll()==null) return null;
		
		return commentDao.getAll().get(0);
	}
	
	@Test
	public void testValidAddPostComment(){
		Post post=getValidPost();
		int oldcount= getPostCommentCount(post);
		
		Comment comment=new Comment();
		comment.setText("Test Comment");
				
		commentDao.addPostComment(post, comment);
		totalcount--;
		
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
		Post post		=getValidPost();
		Comment comment	=getValidPostComment(post);
		
		Comment child=new Comment();
		child.setText("Test Child Comment");
		
		
		int oldcount=getPostCommentCount(post);
		commentDao.addChildComment(comment, child);
		
		totalcount--;
		
		int newcount=getPostCommentCount(post);
		Assert.assertEquals(newcount,oldcount+1);
	}
	@Test(expected=DataAccessException.class)
	public void testAddInvalidChildComment(){
		Post post		=getValidPost();
		Comment comment	=getValidPostComment(post);
		
		Comment child=new Comment();
		commentDao.addChildComment(comment, child);
	}
	
	@Test 
	public void testDeleteValidComment()
	{
		Comment comment=getValidComment();
		Post post=comment.getPost();
		
		int oldtotal=getCommentCount();
		totalcount+=oldtotal;
		
		commentDao.delete(comment);
		int newtotal=totalcount;
		
		Assert.assertEquals(newtotal,oldtotal-1);
		}
}
