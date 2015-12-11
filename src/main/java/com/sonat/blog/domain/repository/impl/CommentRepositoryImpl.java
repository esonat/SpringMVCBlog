package com.sonat.blog.domain.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.util.HibernateUtil;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	public Comment getCommentById(int commentID) {
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.ID= :commentID");
		query.setParameter("commentID", commentID);
		
		if(query.list().size()==0) return null;
		return (Comment)query.list().get(0);
	}	
	
	public List<Comment> getPostComments(int postID){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Query query=session.createQuery("FROM Comment C WHERE C.post.ID= :postID");
		query.setParameter("postID", postID);
		
		if(query.list().size()==0) return null;
		
		return query.list();
	}	

}
