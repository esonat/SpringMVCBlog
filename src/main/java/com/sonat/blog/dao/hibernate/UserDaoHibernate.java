package com.sonat.blog.dao.hibernate;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.User;
import com.sonat.blog.exception.UserNotFoundException;

@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User> implements UserDao{
	 protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	 public UserDaoHibernate() {
		 super(User.class);
	 }

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByName(String name) {
		List<User> result=(List<User>)this.getHibernateTemplate().findByNamedParam("FROM User WHERE name =:name",
				"name",name);

		if(result ==null
		|| result.size()==0) {
			throw new UserNotFoundException("No user found with the name: "+ name);
		}
		return result.get(0);
	}

	 @SuppressWarnings("unchecked")
	 @Override
	 public User getUserByUserName(String username) {
		List<User> result=(List<User>)this.getHibernateTemplate().findByNamedParam("FROM User where username= :username",
				"username",username);

		if(result==null
		|| result.size()==0){
			throw new UserNotFoundException("No user found with the username: "+username);
		}

		return result.get(0);
	}
}
