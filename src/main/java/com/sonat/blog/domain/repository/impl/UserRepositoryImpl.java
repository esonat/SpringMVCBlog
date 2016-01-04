/*package com.sonat.blog.domain.repository.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.domain.repository.UserRepository;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.util.database.HibernateUtil;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	public UserRepositoryImpl(){
	}	
	
	public void addUser(User user){
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(hashedPassword);
		session.save(user);
		
		UserRole userRole=new UserRole(user,"ROLE_USER");
		session.save(userRole);
		
		userRole.setUser(user);
		user.getUserRole().add(userRole);
		
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("From BlogUser");
		return query.list();
	}
	public User getUserByName(String name){
		User user=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("From BlogUser WHERE name =:name");
		query.setParameter("name",name);
		user=(User)query.uniqueResult();
		
		if(user==null) {	
			throw new UserNotFoundException("No user found with the name: "+ name);
		}		
		return user;
	}	
	public User getUserByUsername(String username) {
		User user=null;
		
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("From BlogUser WHERE username =:username");
		query.setParameter("username",username);
		user=(User)query.uniqueResult();
		
		if(user==null) {	
			throw new UserNotFoundException("No user found with the username: "+ username);
		}
		
		return user;
	}
}
*/