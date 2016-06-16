package com.sonat.blog.dao;

import java.util.List;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentDao extends GenericDao<Comment> {
	void 			addChildComment			(Comment parentComment,Comment childComment);
	void			addPostComment			(Post post,Comment comment);
	void			deleteChildComment		(Comment parentComment,int childCommentID);
	List<Comment> 	getAllCommentsByPostId	(int postID);
	List<Comment>   getChildComments		(int commentID);
	List<Comment>   getChildCommentsByDepth	(int commentID,int depth);
	List<Comment>	getCommentsByDepth		(int postID,int depth);
	Comment 		getPostCommentById		(int postID,int commentID);
	List<Comment> 	getPostComments			(int postID);
	//Post 			getPostOfComment		(int commentID);
	//List<Comment> 	searchComments			(String keyword);
}
