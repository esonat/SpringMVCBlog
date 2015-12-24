package com.sonat.blog.domain.repository;

import java.util.List;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public interface CommentRepository {
	void 			addChildComment			(Comment parentComment,Comment childComment);
	void			addPostComment			(Post post,Comment comment);
	void			deleteChildComment		(Comment parentComment,int childCommentID);
	void			deleteComment			(int commentID);	
	List<Comment>	getAllComments();
	List<Comment> 	getAllCommentsByPostId	(int postID);
	List<Comment>   getChildComments		(int commentID);
	List<Comment>   getChildCommentsByDepth	(int commentID,int depth);
	Comment 		getCommentById			(int commentID);
	List<Comment>	getCommentsByDepth		(int postID,int depth);
	Comment 		getPostCommentById		(int postID,int commentID);
	List<Comment> 	getPostComments			(int postID);
	Post 			getPostOfComment		(int commentID);
	Comment getChildCommentById				(int postID,int commentID,int childCommentID);
	//List<Comment> 	searchComments			(String keyword);
	//void 			doIndex() throws InterruptedException;
}

