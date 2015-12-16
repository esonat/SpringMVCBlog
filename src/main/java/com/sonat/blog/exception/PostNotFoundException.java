package com.sonat.blog.exception;

public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3211319429457536575L;
	private int postID;
	
	public PostNotFoundException(int postID){
		this.postID=postID;
	}
	public int getPostID(){
		return postID;
	}
}
