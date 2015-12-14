package com.sonat.blog.domain.repository.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.Cookie;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.hql.ast.tree.SessionFactoryAwareNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.util.ParallelSorter;
import org.springframework.jdbc.core.metadata.PostgresCallMetaDataProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.RequestToViewNameTranslator;

import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class PostRepositoryImpl implements PostRepository{
	@Autowired 
	UserService userService;
	
	public PostRepositoryImpl(){
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
	
	public List<Post> getPostsByCategory(int categoryID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.category.ID= :categoryID");
		query.setParameter("categoryID",categoryID);
	
		if(query.list()==null ||
	       query.list().size()==0) return null;
			
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUsername(String username) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.user.username= :username");
		query.setParameter("username",username);
		return query.list();
	}

	public void addPost(Post post,Category category) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Authentication auth = 	SecurityContextHolder.getContext().getAuthentication();
	    String username = 		auth.getName(); 
		User user		=		userService.getUserByUsername(username);
		
		Date date = new Date();
		
		post.setUser(user);
		post.setDate(date);
		post.setCategory(category);
		
		session.save(post);				
		post.getUser().getPosts().add(post);
		
		session.getTransaction().commit();
	}

	public void deletePost(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();		
		//Query query=session.createQuery("delete Post where ID= :postID");
		Query query=session.createQuery("from Post P where P.ID= :postID");
		query.setParameter("postID", ID);
		session.beginTransaction();

		Post post=(Post)query.list().get(0);
		for(Comment comment:post.getComments()){
			comment.setPost(null);
			session.save(comment);
		}
		
		session.delete(post);
		session.getTransaction().commit();
	}
	
	
}
