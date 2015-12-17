package com.sonat.blog.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import com.sonat.blog.validator.Name;
import com.sonat.blog.validator.Username;


@Entity
@Table(name="user",catalog="blogDB",uniqueConstraints={
@UniqueConstraint(columnNames="NAME")})
public class User {
	@Pattern(regexp="[a-zA-Z]+",message="{Pattern.User.Name.validation}")
	@Name
	private String name;
	private Set<Post> posts=new HashSet<Post>(0);
	@Pattern(regexp="[a-zA-Z]+",message="{Pattern.User.username.validation}")
	@Username
	private String username;
	@Pattern(regexp="^[A-Za-z0-9_.]+$",message="{Pattern.User.password.validation}")	
	@Size(min=5,max=50,message="{Size.User.password.validation}")
	private String password;
	private boolean enabled;
	private Set<UserRole> 	userRole = new HashSet<UserRole>(0);

	public User() {
		super();
	}
	public User(String name) {
		this.name=name;
	}	
	public User(String name,String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String name,String username, String password, boolean enabled, Set<UserRole> userRole) {
		this.name=name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}

	@Column(name="NAME",unique=true,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="user")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JsonIgnore
	public Set<Post> getPosts() {
		return posts;
	}
	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	@Id
	@Column(name="USERNAME",unique=true,nullable=false)
	@JsonIgnore
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="PASSWORD",nullable=false)
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="ENABLED",nullable=false)
	@JsonIgnore
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
