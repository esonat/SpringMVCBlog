package com.sonat.blog.domain.repository.impl;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.servlet.SessionCookieConfig;

import org.apache.taglibs.standard.lang.jstl.Literal;
import org.codehaus.jackson.node.IntNode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.tuple.component.ComponentMetamodel;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	public Post getPostOfComment(int commentID)
	{
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.ID= :commentID");
		query.setParameter("commentID",commentID);
		
		Comment comment=(Comment)query.list().get(0);
		
		Post post=comment.getPost();
		return post;
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
		Query query		=	session.createQuery("FROM Comment C WHERE C.post.ID= :postID");
		
		query.setParameter("postID", postID);
		if(query.list().size()==0) return null;
		
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
	
	public void addPostComment(Post post, Comment comment) {
		Session session=HibernateUtil.getSessionFactory().openSession();				
		session.beginTransaction();
		
		Date date = new Date();	
		
		comment.setDatetime(date);
		comment.setPost(post);
		comment.setDepth(0);
		
		post.getComments().add(comment);
		session.save(comment);
		
		session.getTransaction().commit();
	}

	public void deleteComment(int commentID){
		Session session=HibernateUtil.getSessionFactory().openSession();		
		Query query=session.createQuery("DELETE Comment where ID= :commentID");
		
		session.beginTransaction();
		
		query.setParameter("commentID", commentID);
		query.executeUpdate();
		
		session.getTransaction().commit();
	}

	
	@SuppressWarnings("unchecked")
	public List<Comment> getChildComments(int commentID) {
		return null;
//		Session session=HibernateUtil.getSessionFactory().openSession();
//		Query query=session.createQuery("FROM Comment C WHERE C.parent.ID= :commentID");
//		query.setParameter("commentID",commentID);
//		
//		//if(query.list().size()==0) return null;
//		///Comment comment=(Comment)query.list().get(0);
//		
////		for(Comment childComment:comment.getChildren())
////			childComments.add(childComment);
////		
//		return (List<Comment>)query.list();
//		List<Comment> queryList=new ArrayList<Comment>();
//		queryList=(List<Comment>)query.list();
//		
//		for(int index=0;index<queryList.size();index++){
//			Comment comment=queryList.get(index);
//			if(comment.getParent().getID()==commentID)
//				childComments.add(comment);
//		}
	}
	
	public void addChildComment(Comment parentComment, Comment childComment) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Date date = new Date();	
		Post post =parentComment.getPost();
		
		childComment.setDatetime(date);
		childComment.setDepth(parentComment.getDepth()+1);
		childComment.setPost(post);
		childComment.setParent(parentComment);
		parentComment.getChildren().add(childComment);
		
		session.save(childComment);
		session.save(parentComment);
		
		session.getTransaction().commit();
	}
	public void	deleteChildComment(Comment parentComment,int childCommentID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("DELETE Comment where ID= :childCommentID");
		query.setParameter("childCommentID", childCommentID);
						
		Comment childComment=getCommentById(childCommentID);
	
		session.beginTransaction();
//		for(Comment child:childComment.getChildren()){
//			child.setParent(null);
//			session.update(child);
//		}
		
		session.delete(childComment);
		session.getTransaction().commit();
	}
}
