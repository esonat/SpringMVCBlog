package com.sonat.blog.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.hibernate.search.SearchException;
import org.springframework.stereotype.Repository;

import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.exception.CommentNotFoundException;
//import com.sonat.blog.util.database.HibernateUtil;

@Repository("commentDao")
public class CommentDaoHibernate extends GenericDaoHibernate<Comment> implements CommentDao{
	 protected Log log = LogFactory.getLog(CommentDaoHibernate.class);
	 public CommentDaoHibernate() {
		 super(Comment.class);
	 }

	public void addChildComment(Comment parentComment, Comment childComment) {
	/*	Session session=this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Date date = new Date();
		Post post =parentComment.getPost();

		childComment.setDatetime(date);
		childComment.setDepth(parentComment.getDepth()+1);
		childComment.setPost(post);
		childComment.setParent(parentComment);

		session.save(childComment);
		session.getTransaction().commit();*/
		Date date = new Date();
		Post post =parentComment.getPost();

		childComment.setDatetime(date);
		childComment.setDepth(parentComment.getDepth()+1);
		childComment.setPost(post);
		childComment.setParent(parentComment);

		this.getHibernateTemplate().save(childComment);

	}
	
	public void addPostComment(Post post, Comment comment) {
		Date date = new Date();

		comment.setDatetime(date);
		comment.setPost(post);
		comment.setDepth(0);

		this.getHibernateTemplate().save(comment);
	}

	@SuppressWarnings("unchecked")
	public void deleteChildComment(Comment parentComment, int childCommentID) {
		List<Comment> result=(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment C  where C.ID= :childCommentID","childCommentID",childCommentID);

		if(result==null
		|| result.size()==0) throw new CommentNotFoundException(childCommentID);

		this.getHibernateTemplate().delete(result.get(0));
	}
	@SuppressWarnings("unchecked")
	public List<Comment> getAllCommentsByPostId(int postID) {
		List<Comment> result =	(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment C WHERE C.post.ID= :postID order by C.datetime asc",
				"postID",postID);

		if(result==null
		|| result.size()==0) return null;

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getChildComments(int commentID) {
		List<Comment> result=(List<Comment>) this.getHibernateTemplate().findByNamedParam("FROM Comment C WHERE C.parent.ID= :commentID order by C.datetime asc","commentID",commentID);

		if(result==null
		|| result.size()==0) return null;

		return	(List<Comment>)result;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getChildCommentsByDepth(int commentID, int depth) {
		List<Comment> result=(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment as C WHERE C.parent.ID= :commentID AND C.depth= :depth order by C.datetime asc",
				new String[]{"commentID","depth"},new Object[]{commentID,depth});

		if(result ==null
		|| result.size()==0) return null;

		return (List<Comment>)result;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsByDepth(int postID, int depth) {

		List<Comment> result=(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment as C WHERE C.post.ID=:postID AND C.depth=:depth order by C.datetime asc",
				new String[]{"postID","depth"},new Object[]{postID,depth});

		if(result==null
		|| result.size()==0) return null;

		return (List<Comment>)result;
	}
	@SuppressWarnings("unchecked")
	public Comment getPostCommentById(int postID, int commentID) {
		List<Comment> result=(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment where ID= :commentID and POST_ID= :postID",
				new String[]{"commentID","postID"},new Object[]{commentID,postID});

		if(result==null
		|| result.size()==0) throw new CommentNotFoundException(commentID);

		return result.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<Comment> getPostComments(int postID) {
		List<Comment> result=(List<Comment>)this.getHibernateTemplate().findByNamedParam("FROM Comment C WHERE C.post.ID= :postID",
				"postID",postID);


		if(result==null
		|| result.size()==0) return null;

		return result;
	}
//	@SuppressWarnings("unchecked")
//	@Override
//	public Post getPostOfComment(int commentID) {
//		List<Post> result=(List<Post>)this.getHibernateTemplate().findByNamedParam("FROM Post P WHERE P.ID= :commentID",
//				"commentID",commentID);
//		if(result==null
//		|| result.size()==0) return null;
//
//		return result.get(0);
//	}



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
