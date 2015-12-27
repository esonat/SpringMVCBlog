package com.sonat.blog.domain;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Null;

import org.codehaus.jackson.annotate.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name="category",catalog="blogDB")
//uniqueConstraints={@UniqueConstraint(columnNames="NAME")})
public class Category implements DomainObject{
	
	private int ID;
	private String name;
	private Set<Post> posts=new HashSet<Post>();
    //private Integer version;

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
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="category")
	@JsonIgnore
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
/*	@Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/
}
