package com.sonat.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="post",catalog="blogDB",
uniqueConstraints={@UniqueConstraint(columnNames="TEXT")})
public class Post {
	private int ID;
	private String text;
	private User user;
	//private int UserID;
	
	public Post(){
		super();
	}
	public Post(String text,User user){
		this.text=text;
		this.user=user;
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="POST_ID",unique=true,nullable=false)
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	@Column(name="TEXT",unique=true,nullable=false)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID",nullable=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	
//	
//	public int getUserID(){
//		return UserID;
//	}
//	public void setUserID(int UserID){
//		this.UserID=UserID;
//	}	
}
