package com.sonat.blog.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.impl.CommentServiceImpl;

public class CommentTreeImpl implements CommentTree{
	
	@Autowired
	private CommentServiceImpl 	commentService;
	
	private List<Comment> 		commentList;
	private List<List<Comment>> postComments;
	
	public CommentTreeImpl(List<Post> postList){
		this.commentList	= new ArrayList<Comment>();
		this.postComments	= new ArrayList<List<Comment>>();
		
		for(Post post:postList){
			List<Comment> listComments=new ArrayList<Comment>();
			for(Comment comment:commentService.getPostComments(post.getID())){
				listComments.add(comment);
			}
			postComments.add(listComments);
			//postComments.add(commentService.getPostComments(post.getID()));
		}
			
		for(List<Comment> list:postComments)
			addToList(list);
	}
	
	public void addToList(List<Comment> list){
		for(Comment comment:list){
			if(comment.getChildren()==null) 
				commentList.add(comment);
			else{ 
				List<Comment> children=new ArrayList<Comment>();
				for(Comment child:comment.getChildren()){
					children.add(child);
				}
				addToList(children);
			}
		}
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public List<List<Comment>> getPostComments() {
		return postComments;
	}

	public void setPostComments(List<List<Comment>> postComments) {
		this.postComments = postComments;
	}
	
}
	
//	public List<CommentStruct> getCommentList(List<Post> postList){
//		List<Comment> allComments	= new ArrayList<Comment>();
//		List<CommentStruct> list	= new ArrayList<CommentStruct>();
//
//		for(Post post:postList){
//			for(Comment comment:commentService.getAllCommentsByPostId(post.getID())){
//					allComments.add(comment);
//			}
//		}
//		//allComments.addAll(commentService.getAllCommentsByPostId(post.getID()));
//		
//		if(allComments.size()==0) return null;
//		
//		for(Comment comment:allComments){
//			CommentStruct struct	= new CommentStruct();
//			List<Comment> children	= new ArrayList<Comment>();
//			
//			int commentID	= comment.getID();
//			//int postID	=commentService.getPostOfComment(commentID).getID();
//			int postID= 	  comment.getPost().getID();
//			int parentID	=(comment.getParent()==null)?0:comment.getParent().getID();
//						
//			//children.addAll(commentService.getChildComments(postID,commentID));
//			for(Comment child:commentService.getChildComments(postID, commentID))
//				children.add(child);			
//			
//			struct.setComment(comment);
//			struct.setPostID(postID);	
//			struct.setParentID(parentID);
//			struct.setChildren(children);
//			
//			list.add(struct);
//		}		
//		return list;
//	}	
