package com.sonat.blog.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
//import com.sonat.blog.util.database.HibernateUtil;

@Repository("postDao")
public class PostDaoHibernate extends GenericDaoHibernate<Post> implements PostDao{
	protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	public PostDaoHibernate() {
		 super(Post.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getAllByDate(Date dateFrom, Date dateTo) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery("FROM Post WHERE date <=:dateTo AND date >= :dateFrom");
		
		query.setParameter("dateFrom",new java.sql.Date(dateFrom.getTime()));
		query.setParameter("dateTo", new java.sql.Date(dateTo.getTime()));
		
		if(query.list()==null ||
			       query.list().size()==0) return null;
				
		return (List<Post>)query.list();		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByCategory(int categoryID) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM Post P WHERE P.category.ID= :categoryID order by date asc");
		query.setParameter("categoryID",categoryID);
	
		if(query.list()==null ||
	       query.list().size()==0) return null;
			
		return (List<Post>)query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPostsByUsername(String username) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM Post P WHERE P.user.username= :username order by date asc");
		query.setParameter("username",username);
		return query.list();
	}
	

	 
}
