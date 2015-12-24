package com.sonat.blog.dao.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.util.UserDataAttribute;
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
import com.sonat.blog.util.database.HibernateUtil;

@Repository
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
		
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	@Override
	public User findUserByName(String name) {
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
	
	/** USER DETAILS SERVICE ****/
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		com.sonat.blog.domain.User user = findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(com.sonat.blog.domain.User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}	 
}
