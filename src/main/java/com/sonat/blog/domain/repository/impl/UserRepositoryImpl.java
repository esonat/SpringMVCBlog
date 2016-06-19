package com.sonat.blog.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.UserRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class UserRepositoryImpl implements UserRepository {

	public UserRepositoryImpl(){

	}	
	public User getUserById(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM User WHERE USER_ID =:userID");
		query.setParameter("userID",ID);
		return (User)query.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM User");
		return query.list();
	}	
}
