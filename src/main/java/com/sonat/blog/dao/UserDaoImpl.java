package com.sonat.blog.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sonat.blog.domain.User;
import com.sonat.blog.util.database.HibernateUtil;

public class UserDaoImpl implements UserDao {

//	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM User where username= :username");
		query.setParameter("username",username);
		
		users=query.list();
	//	users = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("FROM User where username= :username");
	//.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}
//
//	public SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
}