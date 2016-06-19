package com.sonat.blog.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cglib.util.ParallelSorter;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class PostRepositoryImpl implements PostRepository{

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
		Query query=session.createQuery("FROM Post P WHERE P.POST_ID="+ID);
		return (Post)query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUserID(int userID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.USER_ID="+userID);
		return query.list();
	}

	public void addPost(Post post) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(post);
				
		post.getUser().getPosts().add(post);
		session.getTransaction().commit();
	}

	public void deletePost(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("delete Post where POST_ID= :postID");
		query.setParameter("postID", ID);
		
		query.executeUpdate();
	}
}
