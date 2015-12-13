package com.sonat.blog.util;

import java.util.List;
import com.sonat.blog.domain.Comment;
import com.sonat.blog.domain.Post;

public class CommentStruct{
	public int postID;
	public Comment parent;
	public List<Comment> children;
	
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	public Comment getParent() {
		return parent;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	public List<Comment> getChildren() {
		return children;
	}
	public void setChildren(List<Comment> children) {
		this.children = children;
	} 
	
	
}
