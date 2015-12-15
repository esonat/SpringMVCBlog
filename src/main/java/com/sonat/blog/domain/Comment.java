package com.sonat.blog.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
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

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="comment",catalog="blogDB")
public class Comment{
	private Set<Comment> children;
	private Date datetime;
	private int depth;
	private int ID;
	private Comment parent;
	private int parentId;
	private Post post;
	private String text;
	
	public Comment(){
		super();
	}
	public Comment(String text,Date datetime,Post post){
		this.text=text;
		this.datetime=datetime;
		this.post=post;		
	}
	public Comment(String text,Post post){
		this.text=text;
		this.post=post;
	}	
	public Comment(String text,Post post,Comment parent){
		this.text=text;
		this.post=post;
		this.parent=parent;
	}
	
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	@Cascade({org.hibernate.annotations.CascadeType.DELETE})
	public Set<Comment> getChildren() {
		return children;
	}
	@Column(name = "DATETIME", columnDefinition="DATETIME",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatetime() {
		return datetime;
	}
	
	@Column(name="DEPTH",nullable=false)
	public int getDepth() {
		return depth;
	}
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="COMMENT_ID",unique=true,nullable=true)
	public int getID() {
		return ID;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PARENT_ID")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	public Comment getParent() {
		return parent;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="POST_ID",nullable=false)
	public Post getPost() {
		return post;
	}
	
	//, insertable = false, updatable = false
	 //@JoinColumn(name="PARENT_ID", insertable = false, updatable = false)
	//@ManyToOne(cascade={CascadeType.ALL})
	
	@Column(name="TEXT",nullable=false)
	public String getText() {
		return text;
	}
	public void setChildren(Set<Comment> children) {
		this.children = children;
	}
	
//	@Column(name = "PARENT_ID",nullable=true)
//	public int getParentId() {
//		return parentId;
//	}
//	public void setParentId(int parentId) {
//		this.parentId = parentId;
//	}
//	
//	@JoinColumn(name="parent", nullable=false)
//	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

	//@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})

	public void setID(int iD) {
		ID = iD;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	public void setPost(Post post) {
		this.post = post;
	}
	public void setText(String text) {
		this.text = text;
	}
}
