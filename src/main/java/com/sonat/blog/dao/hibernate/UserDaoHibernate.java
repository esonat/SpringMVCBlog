package com.sonat.blog.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import orgUserDataAttribute;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.UserRole;
import com.sonat.blog.exception.UserNotFoundException;
//import com.sonat.blog.util.database.HibernateUtil;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User> implements UserDao{
	 protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	 public UserDaoHibernate() {
		 super(User.class);
	 }
	 
	 @Override
	 @SuppressWarnings("unchecked")
	 public User findByUserName(String username) {
		List<User> result=(List<User>)this.getHibernateTemplate().findByNamedParam("FROM User where username= :username",
				"username",username);
		
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	@Override
	public User getUserByName(String name) {
		User result=(User)this.getHibernateTemplate().findByNamedParam("FROM User WHERE name =:name",
				"name",name);
		
		if(result ==null) {	
			throw new UserNotFoundException("No user found with the name: "+ name);
		}		
		return result;
	}
}
