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
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
//uniqueConstraints={@UniqueConstraint(columnNames="TEXT")})
//@Size(min=5,max=500,message="{Size.Post.text.validation}"

@Entity
@Table(name="post",catalog="blogDB")
public class Post {
	private Category category;
	private Set<Comment> comments;
	private Date date;
	private int ID;
	@Size(min=5,max=10000,message="{Size.Post.text.validation}")
	private String text;
	private User user;
	
	public Post(){
		super();
	}
	public Post(String text,User user){
		this.text=text;
		this.user=user;
	}
	public Post(int ID,String text,Date date,Category category,Set<Comment> comments,User user){
		this.ID=ID;
		this.text=text;
		this.date=date;
		this.category=category;
		this.comments=comments;
		this.user=user;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CATEGORY_ID",nullable=false)
	public Category getCategory() {
		return category;
	}
	@OneToMany(fetch=FetchType.EAGER,mappedBy="post")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	public Set<Comment> getComments() {
		return comments;
	}
	@Column(name = "DATE", columnDefinition="DATETIME",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="POST_ID",unique=true,nullable=false)
	@JsonIgnore
	public int getID() {
		return ID;
	}
	
	@Column(name="TEXT",length=10000,nullable=false)
	public String getText() {
		return text;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID",nullable=false)
	public User getUser() {
		return user;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	public void setUser(User user) {
		this.user = user;
	}	
}
