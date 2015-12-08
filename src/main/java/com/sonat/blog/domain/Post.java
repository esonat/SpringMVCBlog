package com.sonat.blog.domain;

public class Post {
	private int ID;
	private String text;
	private int userID;
	private int nextOrderID;
	
	public Post(){
		super();
	}
	public Post(String text,int userID){
		this.text=text;
		this.userID=userID;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
