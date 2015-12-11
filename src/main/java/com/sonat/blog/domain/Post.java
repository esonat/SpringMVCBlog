package com.sonat.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
//uniqueConstraints={@UniqueConstraint(columnNames="TEXT")})
//@Size(min=5,max=500,message="{Size.Post.text.validation}"
@Entity
@Table(name="post",catalog="blogDB")
public class Post {
	private int ID;
	@Size(min=5,max=10000,message="{Size.Post.text.validation}")
	private String text;
	private Date date;
	private User user;
	private Set<Comment> comments;
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
	@Column(name="TEXT",nullable=false)
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
	
	@Column(name = "DATE", columnDefinition="DATETIME",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="post")
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
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
