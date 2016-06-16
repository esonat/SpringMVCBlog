package com.sonat.blog.service;

import java.util.Date;
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

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.PostNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/META-INF/spring/spring-master.xml",
								 "classpath:/META-INF/spring/spring-datasource.xml",
								 "classpath:/META-INF/spring/spring-hibernate.xml"})
@Transactional
@Rollback(true)
public class testCommentService {
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;

	private static final int VALID_POST_ID=18;
	private static final int INVALID_POST_ID=1000;


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
	public void testAddChildComment_validPost_validChild(){
		Post post=postService.getPostById(VALID_POST_ID);

		Comment parent=new Comment();
				parent.setDatetime(new Date());
				parent.setDepth(0);
				parent.setPost(post);
				parent.setText("test comment");

		Comment child=new Comment();
				child.setText("test child comment");

		commentService.addChildComment(VALID_POST_ID,parent,child);
	}

	@Test(expected=DataAccessException.class)
	@Transactional
	@Rollback(true)
	public void testAddChildComment_validPost_invalidChild(){
		Post post=postService.getPostById(VALID_POST_ID);

		Comment parent=new Comment();
				parent.setDatetime(new Date());
				parent.setDepth(0);
				parent.setPost(post);
				parent.setText("test comment");

		Comment child=new Comment();

		commentService.addChildComment(VALID_POST_ID,parent,child);
	}

	@Test(expected=PostNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testAddChildComment_invalidPost_validChild(){
		Post post=postService.getPostById(INVALID_POST_ID);

		Comment parent=new Comment();
				parent.setDatetime(new Date());
				parent.setDepth(0);
				parent.setPost(post);
				parent.setText("test comment");

		Comment child=new Comment();
				child.setText("test child comment");

		commentService.addChildComment(INVALID_POST_ID,parent,child);
	}

	@Test(expected=PostNotFoundException.class)
	@Transactional
	@Rollback(true)
	public void testAddChildComment_invalidPost_invalidChild(){
		Post post=postService.getPostById(INVALID_POST_ID);

		Comment parent=new Comment();
				parent.setDatetime(new Date());
				parent.setDepth(0);
				parent.setPost(post);
				parent.setText("test comment");

		Comment child=new Comment();

		commentService.addChildComment(INVALID_POST_ID,parent,child);
	}



}