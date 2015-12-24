package com.sonat.blog.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.util.UserDataAttribute;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.UserNotFoundException;
import com.sonat.blog.util.database.HibernateUtil;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User> implements UserDao{
	 protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	 public UserDaoHibernate() {
		 super(User.class);
	 }
	 
	 @Override
	 @SuppressWarnings("unchecked")
	 public User findByUserName(String username) {
		List<User> users = new ArrayList<User>();
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
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
	@Override
	public User getUserByName(String name) {
		User user=null;
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM User WHERE name =:name");
		query.setParameter("name",name);
		user=(User)query.uniqueResult();
		
		if(user==null) {	
			throw new UserNotFoundException("No user found with the name: "+ name);
		}		
		return user;
	}
	 
}
