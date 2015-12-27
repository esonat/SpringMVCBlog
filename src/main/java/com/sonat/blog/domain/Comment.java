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
import javax.persistence.Version;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;

import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Index;

import org.hibernate.search.*;
/*import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
*/
@SuppressWarnings("serial")
@Entity
@Table(name="comment",catalog="blogDB")
public class Comment  extends ContentObject implements DomainObject{
	
	private Set<Comment> children;
	private Date datetime;
	private int depth;
	private int ID;
	private Comment parent;
	private Post post;
	private String text;
    //private Integer version;

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
	@JsonIgnore
	public int getID() {
		return ID;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PARENT_ID")
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JsonIgnore
	public Comment getParent() {
		return parent;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="POST_ID",nullable=false)
	@JsonIgnore
	public Post getPost() {
		return post;
	}
	
	//, insertable = false, updatable = false
	 //@JoinColumn(name="PARENT_ID", insertable = false, updatable = false)
	//@ManyToOne(cascade={CascadeType.ALL})
	
	//@Field(index = Index.TOKENIZED, store = Store.NO)
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
/*	@Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/
}
