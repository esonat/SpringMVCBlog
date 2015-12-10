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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sonat.blog.domain.*;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="user",catalog="blogDB",uniqueConstraints={
@UniqueConstraint(columnNames="NAME")})
public class User {
	@Pattern(regexp="[a-zA-Z]+",message="{Pattern.User.Name.validation}")
	private String Name;
	private Set<Post> posts=new HashSet<Post>(0);
	@Pattern(regexp="[a-zA-Z]+",message="{Pattern.User.username.validation}")
	private String username;
	@Pattern(regexp="^[A-Za-z0-9_.]+$",message="{Pattern.User.password.validation}")	
	@Size(min=5,max=50,message="{Size.User.password.validation}")
	private String password;
	private boolean enabled;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	
	public User() {
		super();
	}
	public User(String name) {
		this.Name=name;
	}	
	public User(String name,String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String name,String username, String password, boolean enabled, Set<UserRole> userRole) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}
	
//	public int getID() {
//		return ID;
//	}
//	public void setID(int ID) {
//		this.ID = ID;
//	}
	@Column(name="name",unique=true,nullable=false)
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
	
	@Id
	@Column(name="username",unique=true,nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="password",nullable=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="enabled",nullable=false)
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	public Set<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	
}
