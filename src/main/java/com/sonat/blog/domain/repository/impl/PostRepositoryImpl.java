/*package com.sonat.blog.domain.repository.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.search.FullTextSession;
//import org.hibernate.search.Search;
//import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.User;
import com.sonat.blog.domain.repository.PostRepository;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.UserService;
import com.sonat.blog.util.database.HibernateUtil;
import com.sonat.blog.util.datetime.DateTimeConstants;

@Repository
public class PostRepositoryImpl implements PostRepository{
	@Autowired 
	UserService userService;
	
	public PostRepositoryImpl(){
	}
	
	public void doIndex() throws InterruptedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer().startAndWait();
         
        fullTextSession.close();
    }
	
	public void addPost(Post post,Category category) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		if(post.getUser()==null){
			Authentication auth = 	SecurityContextHolder.getContext().getAuthentication();
		    String username = 		auth.getName(); 
			User user		=		userService.getUserByUsername(username);
			post.setUser(user);	
		}
		
		Date date = new Date();
		post.setDate(date);
		post.setCategory(category);
		
		session.save(post);				
		post.getUser().getPosts().add(post);
		
		session.getTransaction().commit();
	}

	public void deletePost(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();		
		Query query=session.createQuery("from Post P where P.ID= :postID");
		query.setParameter("postID", ID);
		session.beginTransaction();

		Post post=(Post)query.list().get(0);
		for(Comment comment:post.getComments()){
			comment.setPost(null);
			session.save(comment);
		}
		
		session.delete(post);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> getAll() {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P order by P.date asc");
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		List<Post> list=query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Post> getAllByDate(Date dateFrom, Date dateTo){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("FROM Post WHERE date <=:dateTo AND date >= :dateFrom");
		
		query.setParameter("dateFrom",new java.sql.Date(dateFrom.getTime()));
		query.setParameter("dateTo", new java.sql.Date(dateTo.getTime()));
		
		if(query.list()==null ||
			       query.list().size()==0) return null;
				
		return (List<Post>)query.list();		
	}

	public Post getPostById(int ID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.ID= :postID");
		query.setParameter("postID",ID);
		
		if(query.list().size()==0) 
			throw new PostNotFoundException(ID);
		
		return (Post)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostsByCategory(int categoryID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.category.ID= :categoryID order by date asc");
		query.setParameter("categoryID",categoryID);
	
		if(query.list()==null ||
	       query.list().size()==0) return null;
			
		return (List<Post>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> getPostsByUsername(String username) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Post P WHERE P.user.username= :username order by date asc");
		query.setParameter("username",username);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Post> searchPosts(String keyword){
		Session session=HibernateUtil.getSessionFactory().openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
     
        QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Post.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("text").matching(keyword).createQuery();
 
        // wrap Lucene query in a javax.persistence.Query
        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Post.class);
         
        List<Post> list = fullTextQuery.list();
         
        fullTextSession.close();
        return list;
	}
	
}
*/