package com.sonat.blog.util;

import java.security.KeyStore.PrivateKeyEntry;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Stub;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreInvocationAttribute;

import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;
import com.sonat.blog.service.CommentService;
import com.sonat.blog.service.PostService;


public class CommentTree {
	@Autowired
	PostService postService;
	@Autowired 
	CommentService commentService;
	
	private List<CommentStruct> commentList;

	public CommentTree(){
		setCommentList();		
	}
	public List<CommentStruct> getCommentList(){
		return commentList;
	}
	public void setCommentList(){
		List<Comment> allComments=commentService.getAllComments();
		for(Comment comment:allComments){
			CommentStruct struct=new CommentStruct();
			struct.parent=comment;
			struct.children=commentService.getChildComments(comment.getParent().getID(),comment.getID());
			commentList.add(struct);
		}
	}
//
//	private int commentID;
//	private Comment root;
//	private List<Comment> children; 
//	private CommentTree next;
//	private Comment parent;
//	private static int index=0;
//	private int size;
//	private static List<Integer> visited=new ArrayList<Integer>();
//	
//	
//
//	public CommentTree(Comment parent,int commentID,int size){
//		this.commentID=commentID;
//		this.parent=parent;
//		this.size=size;
//		
//		visited.add(parent.getID());
//		if(index!=size) setNext();
//	}
//	public Comment getRoot(){
//		return commentService.getCommentById(commentID);
//	}
//	public void setChildren(){
//		this.children=commentService.getChildComments(root.getPost().getID(),commentID);
//	}
//	public List<Comment> getChildren(){
//		return children;
//	}	
//	Comment[] commentArray;
//
//	public CommentTree setNext(){
//		for(int i=0;i<getChildren().size();i++)
//		{
//			int ID=getChildren().get(i).getID();
//			return new CommentTree(root,getChildren().get(i).getID(),index++);
//		}		
//	}
//	public CommentTree getNext(){
//		return this.next;
//	}	
}
