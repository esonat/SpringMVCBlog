package com.sonat.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

import net.sf.ehcache.constructs.scheduledrefresh.ScheduledRefreshJobStorePropertiesFactory;

import static javax.persistence.GenerationType.IDENTITY;


@SuppressWarnings("serial")
@Entity
@Table(name="user_roles",catalog="blogDB")
public class UserRole implements DomainObject{
	private Integer userRoleId;
	private User user;
	private String role;

	public UserRole() {
		super();
	}

	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="USER_ROLE_ID",unique=true,nullable=false)
	@JsonIgnore
	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USERNAME",nullable=false)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JsonIgnore
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name="ROLE",nullable=false)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
