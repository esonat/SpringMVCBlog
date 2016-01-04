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
import javax.persistence.Version;
import javax.validation.constraints.Size;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
/*import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
*/
import org.hibernate.annotations.CacheConcurrencyStrategy;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Set;
//uniqueConstraints={@UniqueConstraint(columnNames="TEXT")})
//@Size(min=5,max=500,message="{Size.Post.text.validation}"

@SuppressWarnings("serial")
@Entity
//@Indexed
@Table(name="post",catalog="blogDB")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post extends ContentObject implements DomainObject {
	private Category category;
	private Set<Comment> comments;
	private Date date;
	private int ID;
	@Size(min=5,max=1000000,message="{Size.Post.text.validation}")
	private String text;
	private BlogUser user;
    //private Integer version;
	
	public Post(){
		super();
	}
	public Post(String text,BlogUser user){
		this.text=text;
		this.user=user;
	}
	public Post(int ID,String text,Date date,Category category,Set<Comment> comments,BlogUser user){
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
	
	//@Field(index = Index.TOKENIZED,store = Store.NO)
	@Column(name="TEXT",length=10000,nullable=false)
	public String getText() {
		return text;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	public BlogUser getUser() {
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
	public void setUser(BlogUser user) {
		this.user = user;
	}	
	
/*	@Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/
	 @Override
     public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: ").append(this.getID()).append(" | Text:").append(this.getText()).append(" | Date:").append(this.getDate().toString());
         
        return stringBuilder.toString();
     }
}
