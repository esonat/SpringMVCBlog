package com.sonat.blog.dao.hibernate;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.Init;
import javax.resource.spi.RetryableUnavailableException;
import javax.security.auth.Destroyable;
import javax.sound.sampled.LineListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.CommentNotFoundException;

import junit.framework.Assert;

import com.sonat.blog.domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
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
	private static int totalcount;

	private static final int INVALID_COMMENT_ID=1000;
	private static final int VALID_POST_ID=1;
	private static final int INVALID_POST_ID=1000;
	private static final int VALID_PARENT_COMMENT_ID=7;
	private static final int INVALID_PARENT_COMMENT_ID=1000;
	private static final int VALID_CHILD_COMMENT_ID=17;
	private static final int INVALID_CHILD_COMMENT_ID=1000;
	private static final int VALID_DEPTH=1;
	private static final int INVALID_DEPTH=1000;


	Lock sequential = new ReentrantLock();



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
	@Ignore
	public Comment getValidParentComment(){
		if(commentDao.getAll()==null) return null;

		for(Comment item:commentDao.getAll()){
			if(item.getChildren()!=null)
				return item;
		}
		return null;
	}
	@Ignore
	public Comment getValidChildComment(Comment parent){
		return commentDao.getChildComments(parent.getID()).get(0);
	}


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
	public void testValidAddPostComment(){
		Post post=getValidPost();
		int oldcount= getPostCommentCount(post);

		Comment comment=new Comment();
		comment.setText("NEW COMMENT");

		commentDao.addPostComment(post, comment);

		int newcount=getPostCommentCount(post);
		Assert.assertEquals(oldcount+1,newcount);
	}

	@Test(expected=RuntimeException.class)
	@Transactional
	@Rollback(true)
	public void testAddNullPostComment(){
		Post post=null;

		Comment comment=new Comment();
		comment.setText("Test Comment");

		commentDao.addPostComment(post, comment);
	}

	@Test(expected=DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testAddInvalidPostComment(){
		Post post=getValidPost();

		Comment comment=new Comment();
		commentDao.addPostComment(post, comment);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddValidChildComment(){
		Post post		=getValidPost();
		Comment comment	=getValidPostComment(post);

		Comment child=new Comment();
		child.setText("Test Child Comment");


		int oldcount=getPostCommentCount(post);
		commentDao.addChildComment(comment, child);


		int newcount=getPostCommentCount(post);
		Assert.assertEquals(newcount,oldcount+1);
	}
	@Test(expected=DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testAddInvalidChildComment(){
		Post post		=getValidPost();
		Comment comment	=getValidPostComment(post);


		Comment child=new Comment();
		commentDao.addChildComment(comment, child);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteValidComment()
	{
		Comment comment=commentDao.get(21);
		commentDao.delete(comment);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testValidDeleteChildComment(){
		Comment comment	=getValidParentComment();
		Comment child	=getValidChildComment(comment);

		if(comment == null) return;

		int oldcount=commentDao.getChildComments(comment.getID()).size();
		commentDao.deleteChildComment(comment,child.getID());

		int newcount=commentDao.getChildComments(comment.getID()).size();
		Assert.assertEquals(oldcount-1,newcount);
	}

	@Test(expected=CommentNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testInvalidDeleteChildComment(){
		Comment comment=getValidParentComment();

		if(comment==null) return;

		commentDao.deleteChildComment(comment, INVALID_COMMENT_ID);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllCommentsByValidPostId(){
		List<Comment> list=commentDao.getAllCommentsByPostId(VALID_POST_ID);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetAllCommentsByInvalidPostId(){
		List<Comment> list=commentDao.getAllCommentsByPostId(INVALID_POST_ID);
		Assert.assertNull(list);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testValidGetChildComments(){
		List<Comment> list=commentDao.getChildComments(VALID_PARENT_COMMENT_ID) ;
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testInvalidGetChildComments(){
		List<Comment> list=commentDao.getChildComments(INVALID_PARENT_COMMENT_ID) ;
		Assert.assertNull(list);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetValidChildCommentsByDepth(){
		List<Comment> list=commentDao.getChildCommentsByDepth(VALID_PARENT_COMMENT_ID,VALID_DEPTH);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.size()==0);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetInvalidChildCommentsByDepth(){
		List<Comment> list=commentDao.getChildCommentsByDepth(INVALID_PARENT_COMMENT_ID,VALID_DEPTH);
		Assert.assertNull(list);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetValidChildCommentsByInvalidDepth(){
		List<Comment> list=commentDao.getChildCommentsByDepth(VALID_PARENT_COMMENT_ID,INVALID_DEPTH);
		Assert.assertNull(list);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetInvalidChildCommentsByInvalidDepth(){
		List<Comment> list=commentDao.getChildCommentsByDepth(INVALID_PARENT_COMMENT_ID,INVALID_DEPTH);
		Assert.assertNull(list);
	}
}
