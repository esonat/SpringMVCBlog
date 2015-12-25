package com.sonat.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sonat.blog.dao.CommentDao;
import com.sonat.blog.dao.PostDao;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.domain.repository.CommentRepository;
import com.sonat.blog.domain.repository.impl.CommentRepositoryImpl;
import com.sonat.blog.exception.CommentNotFoundException;
import com.sonat.blog.exception.PostNotFoundException;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

	/*@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostService postService;*/
	
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private PostService postService;
	
	private List<Comment> visited;
	
	public CommentServiceImpl(){
		visited=new ArrayList<Comment>();
	}
	public void addChildComment(int postID,Comment parentComment,Comment childComment){
		Post post			=	postService.getPostById(postID);
		if(post==null) return;
			
		commentDao.addChildComment(parentComment, childComment);
	}
	public void addPostComment(int postID, Comment comment) {
		Post post=postService.getPostById(postID);
		if(post==null) return;
		
		commentDao.addPostComment(post,comment);
	}
	
	public void deleteChildComment(int postID,int commentID,int childCommentID){
//		Post post			=	postService.getPostById(postID);
//		if(post==null) return;
		
		Comment parentComment=getCommentById(commentID);
		commentDao.deleteChildComment(parentComment,childCommentID);
	}
	
	public void deleteComment(int postID, int commentID) {
		Post post=postService.getPostById(postID);
		if(post==null) return;
		
		Comment comment=commentDao.get(commentID);
		commentDao.delete(comment);
	}

	public void findNext(Comment comment,int depth){
		if(comment.getChildren().size()>0){
			int commentID			= comment.getID();
			List<Comment> children	= getChildCommentsByDepth(commentID, depth+1);
			
			visited.add(comment);
			if(children!=null)
				for(Comment child:children) findNext(child,depth+1);
		
		}else visited.add(comment);
	}

	public List<Comment> getAllComments(){
		CommentRepository commentRepository=new CommentRepositoryImpl();
		return commentRepository.getAllComments();
	}

	public List<Comment> getAllCommentsByPostId(int postID){
	    postService.getPostById(postID);
		return commentDao.getAllCommentsByPostId(postID);
	}	
	public Comment getChildCommentById(int postID,int commentID,int childCommentID)
	throws PostNotFoundException,CommentNotFoundException{
		Post 	post		=	postService.getPostById(postID);
		//		return commentDao.getChildCommentById(postID,commentID,childCommentID);
		return commentDao.get(childCommentID);
	}
	public List<Comment>   getChildComments	(int postID,int commentID){
		Post post			=	postService.getPostById(postID);
		if(post==null) return null;
		
		return commentDao.getChildComments(commentID);		
	}
	public List<Comment>   getChildCommentsByDepth(int commentID,int depth){
		return commentDao.getChildCommentsByDepth(commentID,depth);
	}
	public List<Comment> getChildCommentTree(Comment comment){
		//int postID=comment.getPost().getID();
		visited=new ArrayList<Comment>();
		visited.add(comment);
		
		List<Comment> commentList=getChildCommentsByDepth(comment.getID(),comment.getDepth()+1);
		if(commentList==null) return visited;
		
		for(Comment child:commentList){
			findNext(child, comment.getDepth()+1);
		}
		return visited;		 				
	}

	public Comment getCommentById(int commentID) {
		return commentDao.get(commentID);
	}
	
	public List<Comment> getCommentsByDepth(int postID,int depth){
		return commentDao.getCommentsByDepth(postID,depth);
	}
	public List<Comment> getCommentTree(Post post){
		int postID=post.getID();
		visited=new ArrayList<Comment>();
		List<Comment> commentList=getCommentsByDepth(postID,0);
		
		if(commentList==null) return null;
		
		for(Comment comment:commentList){
			findNext(comment,0);			
		}
		//for(Comment comment:visited) result.add(comment);
		return visited;
	}
	
	public Comment getPostCommentById(int postID,int commentID)
	throws CommentNotFoundException,PostNotFoundException{
		Post post=postService.getPostById(postID);
		if(post==null) return null;
		
		return commentDao.getPostCommentById(postID,commentID);
	}	  
	
	public List<Comment> getPostComments(int postID) {
		Post post	=	postService.getPostById(postID);
		if(post==null) return null;
		
		return commentDao.getPostComments(postID);
	}
	
	public Post getPostOfComment(int commentID){
		return commentDao.getPostOfComment(commentID);
	}
}
