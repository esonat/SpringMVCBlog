package com.sonat.blog.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.omg.CORBA.UnknownUserException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.action.ResultEventFactory;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.dao.UserDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
//import com.sonat.blog.util.database.HibernateUtil;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.security.SecurityUtilInterface;

@Repository("postDao")
@Transactional
public class PostDaoHibernate extends GenericDaoHibernate<Post> implements PostDao{
	
	@Autowired
	private SecurityUtilInterface securityUtil;
	@Autowired
	private UserService userService;
	
	protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	
	public PostDaoHibernate() {
		 super(Post.class);
	}

	@Transactional(rollbackFor=DataAccessException.class, readOnly=false, timeout=30, propagation=Propagation.SUPPORTS, isolation=Isolation.DEFAULT)
	public void addPost(Post post,Category category){
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		session.beginTransaction();
		
		post.setCategory(category);
		post.setDate(new Date());
		
		Authentication auth = 	SecurityContextHolder.getContext().getAuthentication();
	    String username = 		auth.getName(); 
		User user	=		userService.getUserByUsername(username);

		post.setUser(user);
		post.getUser().getPosts().add(post);
		
		session.save(post);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteById(int ID) {
		Session session=this.getHibernateTemplate().getSessionFactory().openSession();
		Query query	= session.createQuery("FROM Post WHERE ID= :ID");
		query.setParameter("ID", ID);
		
		Post post=(Post)query.uniqueResult();
		if(post==null) throw new PostNotFoundException(ID);
				
		session.beginTransaction();
		
		for(Comment comment:post.getComments()){
			comment.setPost(null);
			session.save(comment);
		}
		
		session.delete(post);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllByDate(Date dateFrom, Date dateTo) {
		List<Post> result= (List<Post>)this.getHibernateTemplate().findByNamedParam("FROM Post WHERE date <=:dateTo AND date >= :dateFrom",
				new String[]{"dateFrom","dateTo"},new java.sql.Date[]{new java.sql.Date(dateFrom.getTime()),new java.sql.Date(dateTo.getTime())});
		
		
		if(result==null ||
			       result.size()==0) return null;
				
		return (List<Post>)result;		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByCategory(int categoryID) {
		List<Post> result=(List<Post>)this.getHibernateTemplate().findByNamedParam("FROM Post P WHERE P.category.ID= :categoryID order by date asc",
				"categoryID",categoryID);
		
		if(result==null ||
	       result.size()==0) return null;
			
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByUsername(String username) {
		List<Post> result=(List<Post>)this.getHibernateTemplate().findByNamedParam("FROM Post P WHERE P.user.username= :username order by date asc",
				"username",username);
		
		return result;
	}
	

	 
}
