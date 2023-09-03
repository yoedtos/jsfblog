package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import net.yoedtos.blog.model.dto.PostDTO;

/**
 * Entity implementation class for Entity: Post
 *
 */
@SuppressWarnings("serial")
@Entity
@Indexed
@NamedQueries({
	@NamedQuery(name="Post.loadAll", query="SELECT p FROM Post p"),
	@NamedQuery(name="Post.loadAllByUser", query="SELECT p FROM Post p WHERE p.author.username = :user"),
	@NamedQuery(name="Post.loadPostTotal", query="SELECT COUNT(p) FROM Post p"),
	@NamedQuery(name="Post.loadLastBetween", 
				query="SELECT p.id, p.createdAt, p.title, p.author, p.intro FROM Post p ORDER BY p.createdAt DESC")
})
public class Post implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="post_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createdAt;
	@Column(length = 55)
	@Field(store = Store.YES)
	private String title;
	@OneToOne
	private User author;
	@OneToOne
	private Category category;
	@Column(length=450)
	@Field
	private String intro;
	@Lob
	private String content;
	@Column(length=200)
	@Field(store = Store.YES)
	private String metaDesc;
	@Column(length=100)
	@Field
	private String metaKey;
	
	public Post() {
		super();
	}   

	private Post(Builder builder) {
		super();
		this.id = builder.id;
		this.createdAt = builder.createdAt;
		this.title = builder.title;
		this.author = builder.author;
		this.category = builder.category;
		this.intro = builder.intro;
		this.content = builder.content;
		this.metaDesc = builder.metaDesc;
		this.metaKey = builder.metaKey;
	}
	
	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}   

	public String getTitle() {
		return this.title;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getIntro() {
		return intro;
	}
	
	public String getContent() {
		return this.content;
	}

	public String getMetaDesc() {
		return metaDesc;
	}

	public String getMetaKey() {
		return metaKey;
	}
	
	public static Post convert(PostDTO postDTO) {
		return new Post.Builder()
				.id(postDTO.getId())
				.createdAt(postDTO.getCreatedAt())
				.title(postDTO.getTitle())
				.intro(postDTO.getIntro())
				.content(postDTO.getContent())
				.metaDesc(postDTO.getMetaDesc())
				.metaKey(postDTO.getMetaKey())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Date createdAt;
		private String title;
		private User author;
		private Category category;
		private String intro;
		private String content;
		private String metaDesc;
		private String metaKey;
		
		public Builder() {}
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder createdAt(Date date) {
			this.createdAt = date;
			return this;
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder author(User author) {
			this.author = author;
			return this;
		}
		
		public Builder category(Category category) {
			this.category = category;
			return this;
		}
		
		public Builder intro(String intro) {
			this.intro = intro;
			return this;
		}
		
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		public Builder metaDesc(String metaDesc) {
			this.metaDesc = metaDesc;
			return this;
		}
		
		public Builder metaKey(String metaKey) {
			this.metaKey = metaKey;
			return this;
		}
		
		public Post build() {
			return new Post(this);
		}
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", createdAt=" + createdAt + ", title=" + title + ", author=" + author + ", category="
				+ category + ", intro=" + intro + ", content=" + content + ", metaDesc=" + metaDesc + ", metaKey="
				+ metaKey + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, category, content, createdAt, id, intro, metaDesc, metaKey, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return Objects.equals(author, other.author) && Objects.equals(category, other.category)
				&& Objects.equals(content, other.content) && Objects.equals(createdAt.getTime()/1000, other.createdAt.getTime()/1000)
				&& Objects.equals(id, other.id) && Objects.equals(intro, other.intro)
				&& Objects.equals(metaDesc, other.metaDesc) && Objects.equals(metaKey, other.metaKey)
				&& Objects.equals(title, other.title);
	}
}
