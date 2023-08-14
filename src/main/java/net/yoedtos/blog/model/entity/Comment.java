package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

import net.yoedtos.blog.model.dto.CommentDTO;

/**
 * Entity implementation class for Entity: Comment
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name="Comment.loadAll", query="SELECT c FROM Comment c"),
	@NamedQuery(name="Comment.loadAllByPostId", query="SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.id = :id")
})
public class Comment implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="comment_id")
	private Long id;
	@Column(length = 50)
	private String author;
	@Temporal(TIMESTAMP)
	private Date createAt;
	@Column(length = 500)
	private String content;
	@Column(length = 40)
	private String email;
	private String hostAddress;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public Comment() {
		super();
	}   
	
	private Comment(Builder builder) {
		super();
		this.id = builder.id;
		this.author = builder.author;
		this.createAt = builder.createAt;
		this.content = builder.content;
		this.email = builder.email;
		this.hostAddress = builder.hostAddress;
		this.post = builder.post;
	}   
	
	public Long getId() {
		return id;
	}

	public String getAuthor() {
		return this.author;
	}
	
	public Date getCreateAt() {
		return createAt;
	}

	public String getContent() {
		return content;
	}

	public String getEmail() {
		return email;
	}
	
	public String getHostAddress() {
		return hostAddress;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public static Comment convert(CommentDTO commentDTO) {
		return new Comment.Builder(commentDTO.getAuthor())
				.id(commentDTO.getId())
				.createAt(commentDTO.getCreateAt())
				.content(commentDTO.getContent())
				.email(commentDTO.getEmail())
				.hostAddress(commentDTO.getHostAddress())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private String author;
		private Date createAt;
		private String content;
		private String email;
		private String hostAddress;
		private Post post;
		
		public Builder(String author) {
			this.author = author;
		}
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder hostAddress(String hostAddress) {
			this.hostAddress = hostAddress;
			return this;
		}
		
		public Builder post(Post post) {
			this.post = post;
			return this;
		}
		
		public Comment build() {
			return new Comment(this);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id);
	}
}
