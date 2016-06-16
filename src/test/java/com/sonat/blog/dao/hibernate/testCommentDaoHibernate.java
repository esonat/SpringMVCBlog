package com.sonat.blog.dao.hibernate;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CommentNotFoundException;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
public class testCommentDaoHibernate {

	@Autowired
	private PostDao postDao;
	@Autowired
	private CommentDao commentDao;

	private static final int INVALID_COMMENT_ID=1000;
	private static final int VALID_POST_ID=1;
	private static final int INVALID_POST_ID=1000;
	private static final int VALID_PARENT_COMMENT_ID=7;
	private static final int INVALID_PARENT_COMMENT_ID=1000;
//	private static final int VALID_CHILD_COMMENT_ID=17;
//	private static final int INVALID_CHILD_COMMENT_ID=1000;
	private static final int VALID_DEPTH=1;
	private static final int INVALID_DEPTH=1000;
	private static final int VALID_POST_COMMENT_SIZE=11;

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
		Comment comment=getValidComment();
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
		int validPostID=getValidComment().getPost().getID();
		List<Comment> list=commentDao.getAllCommentsByPostId(validPostID);
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
	@Test
	@Transactional
	@Rollback(true)
	public void testGetCommentsByDepth_validPost_validDepth()
	{
		List<Comment> list=commentDao.getCommentsByDepth(VALID_POST_ID,VALID_DEPTH);
		Assert.assertNotNull(list);
		Assert.assertEquals(list.size(),3);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetCommentsByDepth_validPost_invalidDepth()
	{
		List<Comment> list=commentDao.getCommentsByDepth(VALID_POST_ID,INVALID_DEPTH);
		Assert.assertNull(list);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetCommentsByDepth_invalidPost_validDepth()
	{
		List<Comment> list=commentDao.getCommentsByDepth(INVALID_POST_ID,VALID_DEPTH);
		Assert.assertNull(list);
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testGetCommentsByDepth_invalidPost_invalidDepth()
	{
		List<Comment> list=commentDao.getCommentsByDepth(INVALID_POST_ID,INVALID_DEPTH);
		Assert.assertNull(list);
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testGetPostCommentById_validPost_validComment()
	{
		Comment comment=commentDao.getPostCommentById(VALID_POST_ID,VALID_PARENT_COMMENT_ID);
		Assert.assertNotNull(comment);
		Assert.assertEquals(comment.getPost().getID(),VALID_POST_ID);
	}

	@Test(expected=CommentNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostCommentById_validPost_invalidComment()
	{
		Comment comment=commentDao.getPostCommentById(VALID_POST_ID,INVALID_PARENT_COMMENT_ID);
		Assert.assertNull(comment);
	}

	@Test(expected=CommentNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostCommentById_invalidPost_validComment()
	{
		Comment comment=commentDao.getPostCommentById(INVALID_POST_ID,VALID_PARENT_COMMENT_ID);
		Assert.assertNull(comment);
	}

	@Test(expected=CommentNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testGetPostCommentById_invalidPost_invalidComment()
	{
		Comment comment=commentDao.getPostCommentById(INVALID_POST_ID,INVALID_PARENT_COMMENT_ID);
		Assert.assertNull(comment);
	}

	@Test
	@Transactional
	@Rollback(true)
	 public void testGetPostComments_validPost(){
		 List<Comment> list=commentDao.getPostComments(VALID_POST_ID);
		 Assert.assertNotNull(list);
		 Assert.assertEquals(list.size(),VALID_POST_COMMENT_SIZE);
	 }

	@Test
	@Transactional
	@Rollback(true)
	 public void testGetPostComments_invalidPost(){
		 List<Comment> list=commentDao.getPostComments(INVALID_POST_ID);
		 Assert.assertNull(list);
	 }
}
