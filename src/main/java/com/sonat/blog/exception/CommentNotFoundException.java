package com.sonat.blog.exception;

public class CommentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3211319429457536575L;
	private int commentID;
	
	public CommentNotFoundException(int commentID){
		this.commentID=commentID;
	}
	public int getCommentID(){
		return commentID;
	}
}
