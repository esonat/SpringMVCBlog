package com.sonat.blog.domain.repository.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class PostRepositoryImpl implements PostRepository{
	@Autowired 
	UserService userService;
	
	public PostRepositoryImpl(){
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
	
	@SuppressWarnings("unchecked")
	public List<Post> getAll() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P order by P.date asc");
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		List<Post> list=query.list();
		return list;
	}

	public Post getPostById(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.ID= :postID");
		query.setParameter("postID",ID);
		
		if(query.list().size()==0) 
			throw new PostNotFoundException(ID);
		
		return (Post)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByCategory(int categoryID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.category.ID= :categoryID order by date asc");
		query.setParameter("categoryID",categoryID);
	
		if(query.list()==null ||
	       query.list().size()==0) return null;
			
		return (List<Post>)query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUsername(String username) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.user.username= :username order by date asc");
		query.setParameter("username",username);
		return query.list();
	}
	
	
}
