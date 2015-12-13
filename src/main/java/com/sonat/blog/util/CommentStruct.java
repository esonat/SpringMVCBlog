package com.sonat.blog.util;

import java.util.List;
import com.sonat.blog.domain.Comment;

public class CommentStruct{
	public int postID;
	public int parentID;
	public Comment comment;
	public List<Comment> children;
	
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}	
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public List<Comment> getChildren() {
		return children;
	}
	public void setChildren(List<Comment> children) {
		this.children = children;
	} 
	
	
}
