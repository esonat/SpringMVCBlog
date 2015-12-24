package com.sonat.blog.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchException;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CategoryDao;
import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.domain.Category;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CommentNotFoundException;
import com.sonat.blog.util.database.HibernateUtil;

@Repository("commentDao")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment> implements CommentDao{
	 protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	 public CommentDaoHibernate() {
		 super(Comment.class);
	 }
	 
	@Override
	public void addChildComment(Comment parentComment, Comment childComment) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Date date = new Date();	
		Post post =parentComment.getPost();
		
		childComment.setDatetime(date);
		childComment.setDepth(parentComment.getDepth()+1);
		childComment.setPost(post);
		childComment.setParent(parentComment);
		
		session.save(childComment);
		session.getTransaction().commit();
		
	}
	@Override
	public void addPostComment(Post post, Comment comment) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();				
		session.beginTransaction();
		
		Date date = new Date();	
		
		comment.setDatetime(date);
		comment.setPost(post);
		comment.setDepth(0);
		
		session.save(comment);
		session.getTransaction().commit();		
	}
	@Override
	public void deleteChildComment(Comment parentComment, int childCommentID) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM Comment C  where C.ID= :childCommentID");
		query.setParameter("childCommentID", childCommentID);
		
		Comment childComment=this.get(childCommentID);
		session.beginTransaction();
		
		session.delete(childComment);
		session.getTransaction().commit();
		
	}
	@Override
	public List<Comment> getAllCommentsByPostId(int postID) {
		Session session	=	this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		org.hibernate.Query query		=	session.createQuery("FROM Comment C WHERE C.post.ID= :postID order by C.datetime asc");
		
		query.setParameter("postID", postID);
		if(query.list().size()==0) return null;
		
		return (List<Comment>)query.list();		
	}
	@Override
	public List<Comment> getChildComments(int commentID) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		org.hibernate.Query query=session.createQuery("FROM Comment C WHERE C.parent.ID= :commentID order by C.datetime asc");
		query.setParameter("commentID",commentID);
		
		if(query.list()==null) return null;
		
		return query.list();
	}
	@Override
	public List<Comment> getChildCommentsByDepth(int commentID, int depth) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM Comment as C WHERE C.parent.ID= :commentID AND C.depth= :depth order by C.datetime asc");
		query.setParameter("commentID",commentID);
		query.setParameter("depth", depth);
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		return (List<Comment>)query.list();	
	}
	@Override
	public List<Comment> getCommentsByDepth(int postID, int depth) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();	
		org.hibernate.Query query=session.createQuery("FROM Comment as C WHERE C.post.ID=:postID AND C.depth=:depth order by C.datetime asc");
		query.setParameter("postID",postID);
		query.setParameter("depth", depth);
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		return (List<Comment>)query.list();		
	}
	@Override
	public Comment getPostCommentById(int postID, int commentID) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();		
		org.hibernate.Query query=session.createQuery("FROM Comment where ID= :commentID and POST_ID= :postID");
		
		query.setParameter("commentID",commentID);
		query.setParameter("postID",postID);
	
		if(query.list().size()==0)
			throw new CommentNotFoundException(commentID);
		
		return (Comment)query.list().get(0);	
	}
	@Override
	public List<Comment> getPostComments(int postID) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query=session.createQuery("FROM Comment C WHERE C.post.ID= :postID");
		
		query.setParameter("postID", postID);
		if(query.list().size()==0) return null;
		
		return (List<Comment>)query.list();
	}
	@Override
	public Post getPostOfComment(int commentID) {

		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();	
		org.hibernate.Query query=session.createQuery("FROM Comment C WHERE C.ID= :commentID");
		query.setParameter("commentID",commentID);
		
		Comment comment=(Comment)query.list().get(0);
		
		Post post=comment.getPost();
		return post;
	}
	/*@Override
	public List<Comment> searchComments(String keyword) {
		Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();	
		FullTextSession fullTextSession = Search.getFullTextSession(session);
     
        org.hibernate.search.query.dsl.v2.QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Comment.class).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields("text").matching(keyword).createQuery();
        
        // wrap Lucene query in a javax.persistence.Query
        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, Comment.class);
       
        List<Comment> list = fullTextQuery.list();
         
        fullTextSession.close();
        return list;
	}	   */
}
