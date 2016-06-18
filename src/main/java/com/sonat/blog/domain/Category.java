package com.sonat.blog.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

@SuppressWarnings("serial")
@Entity
@Table(name="category",catalog="blogDB")
public class Category implements DomainObject{
	
	private int ID;
	private String name;
	private Set<Post> posts=new HashSet<Post>();

	public Category(){
		super();
	}
	public Category(String name) {
		this.name=name;
	}
	public Category(String name,Set<Post> posts){
		this.name=name;
		this.posts=posts;
	}
	
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="CATEGORY_ID",nullable=false,unique=true)
	@JsonIgnore
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}	
	
	@Column(name="NAME",unique=true,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="category")
	@JsonIgnore
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}
