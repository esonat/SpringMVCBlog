package com.sonat.blog.domain.repository.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.util.ParallelSorter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class PostRepositoryImpl implements PostRepository{
	//List<Post> postList=new ArrayList<Post>(); 
	//private int nextPostId;
	@Autowired 
	UserService userService;
	public PostRepositoryImpl(){
//		Post post1=new Post("post1",1);
//		post1.setID(1);
//		Post post2=new Post("post2",2);
//		post2.setID(2);
//		Post post3=new Post("post3",1);
//		post3.setID(3);
//		Post post4=new Post("post4",2);
//		post4.setID(4);
//		Post post5=new Post("post5",1);
//		post5.setID(5);
//		
//		postList.add(post1);
//		postList.add(post2);
//		postList.add(post3);
//		postList.add(post4);
//		postList.add(post5);
//		
//		nextPostId=6;
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> getAll() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post");
		return query.list();		
	}

	public Post getPostById(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.ID= :postID");
		query.setParameter("postID",ID);
		
		if(query.list().size()==0) return null;
		
		return (Post)query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUsername(String username) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.user.username= :username");
		query.setParameter("username",username);
		return query.list();
	}

	public void addPost(Post post) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName(); 
		User user=userService.getUserByUsername(username);
		
		post.setUser(user);
		Date date = new Date();
		post.setDate(date);
		
		session.save(post);				
		post.getUser().getPosts().add(post);
		session.getTransaction().commit();
	}

	public void deletePost(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("delete Post where ID= :postID");
		query.setParameter("postID", ID);
		
		query.executeUpdate();
	}
}
