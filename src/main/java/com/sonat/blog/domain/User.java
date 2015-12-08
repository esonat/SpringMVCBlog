package com.sonat.blog.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="user",catalog="blogDB",uniqueConstraints={
@UniqueConstraint(columnNames="NAME")})
public class User {
	private int ID;
	private String Name;
	private Set<Post> posts=new HashSet<Post>(0);
	
	public User() {
		super();
	}
	public User(String name) {
		this.Name=name;
	}	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="USER_ID",unique=true,nullable=false)
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	@Column(name="USER_NAME",unique=true,nullable=false)
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
}
