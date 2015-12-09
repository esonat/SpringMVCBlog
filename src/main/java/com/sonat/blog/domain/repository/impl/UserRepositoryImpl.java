package com.sonat.blog.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.domain.repository.UserRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class UserRepositoryImpl implements UserRepository {
//	List<User> userList=new ArrayList<User>(); 
//	private int nextUserId;
	
	public UserRepositoryImpl(){
//		User user1=new User("user1");
//		user1.setID(1);
//		User user2=new User("user2");
//		user2.setID(2);
//		
//		userList.add(user1);
//		userList.add(user2);
//		
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
	public void addUser(User user){
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.save(user);
		
		UserRole userRole=new UserRole(user,"ROLE_USER");
		session.save(userRole);
		
		userRole.setUser(user);
		user.getUserRole().add(userRole);
		
		session.getTransaction().commit();
	}
}
