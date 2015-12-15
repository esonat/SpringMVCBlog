package com.sonat.blog.domain.repository.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	public void addChildComment(Comment parentComment, Comment childComment) {
		Session session=HibernateUtil.getSessionFactory().openSession();
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
	
	public void addPostComment(Post post, Comment comment) {
		Session session=HibernateUtil.getSessionFactory().openSession();				
		session.beginTransaction();
		
		Date date = new Date();	
		
		comment.setDatetime(date);
		comment.setPost(post);
		comment.setDepth(0);
		
		session.save(comment);
		session.getTransaction().commit();
	}
	public void	deleteChildComment(Comment parentComment,int childCommentID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C  where C.ID= :childCommentID");
		query.setParameter("childCommentID", childCommentID);
		
		Comment childComment=getCommentById(childCommentID);
		session.beginTransaction();
		
		session.delete(childComment);
		session.getTransaction().commit();
	}
	public void deleteComment(int commentID){
		Session session=HibernateUtil.getSessionFactory().openSession();		
		Query query=session.createQuery("FROM Comment C where C.ID= :commentID");
		
		session.beginTransaction();
		query.setParameter("commentID", commentID);
		
		if(query.list()==null) return;
		Comment comment=(Comment)query.uniqueResult();
		
		session.delete(comment);
		session.getTransaction().commit();
	}		
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllComments(){
		Session session	=	HibernateUtil.getSessionFactory().openSession();
		Query query		=	session.createQuery("FROM Comment order by datetime asc");
		
		if(query.list()==null) return null;
		if(query.list().size()==0) return null;
		
		return (List<Comment>)query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getAllCommentsByPostId(int postID){
		Session session	=	HibernateUtil.getSessionFactory().openSession();
		Query query		=	session.createQuery("FROM Comment C WHERE C.post.ID= :postID order by C.datetime asc");
		
		query.setParameter("postID", postID);
		if(query.list().size()==0) return null;
		
		return (List<Comment>)query.list();		
	}	
	
	@SuppressWarnings("unchecked")
	public List<Comment> getChildComments(int commentID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.parent.ID= :commentID order by C.datetime asc");
		query.setParameter("commentID",commentID);
		
		if(query.list()==null) return null;
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment>   getChildCommentsByDepth(int commentID,int depth){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment as C WHERE C.parent.ID= :commentID AND C.depth= :depth order by C.datetime asc");
		query.setParameter("commentID",commentID);
		query.setParameter("depth", depth);
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		return (List<Comment>)query.list();		
	}

	
	public Comment getCommentById(int commentID) {
		Session session	=	HibernateUtil.getSessionFactory().openSession();
		Query query		=	session.createQuery("FROM Comment C WHERE C.ID= :commentID");
		
		query.setParameter("commentID", commentID);
		if(query.list().size()==0) return null;
		
		Comment comment=(Comment)query.list().get(0);
		session.close();
		return comment;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByDepth(int postID,int depth){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment as C WHERE C.post.ID=:postID AND C.depth=:depth order by C.datetime asc");
		query.setParameter("postID",postID);
		query.setParameter("depth", depth);
		
		if(query.list()==null
		|| query.list().size()==0) return null;
		
		return (List<Comment>)query.list();		
	}
	public Comment getPostCommentById(int postID, int commentID){
		Session session=HibernateUtil.getSessionFactory().openSession();		
		Query query=session.createQuery("FROM Comment where ID= :commentID and POST_ID= :postID");
		
		query.setParameter("commentID",commentID);
		query.setParameter("postID",postID);
	
		if(query.list().size()==0) return null;
		
		return (Comment)query.list().get(0);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getPostComments(int postID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.post.ID= :postID");
		
		query.setParameter("postID", postID);
		if(query.list().size()==0) return null;
		
		return (List<Comment>)query.list();
	}
	
	public Post getPostOfComment(int commentID)
	{
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.ID= :commentID");
		query.setParameter("commentID",commentID);
		
		Comment comment=(Comment)query.list().get(0);
		
		Post post=comment.getPost();
		return post;
	}
}
