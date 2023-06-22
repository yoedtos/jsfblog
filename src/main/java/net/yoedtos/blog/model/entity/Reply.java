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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import net.yoedtos.blog.model.dto.ReplyDTO;

/**
 * Entity implementation class for Entity: Reply
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQueries({
	@NamedQuery(name="Reply.loadAll", query="SELECT r FROM Reply r"),
	@NamedQuery(name="Reply.findAllByCommentId", query="SELECT r FROM Reply r WHERE r.comment.id = :commentId"),
})
public class Reply implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="reply_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createAt;
	@Column(length = 1000)
	private String content;
	private String hostAddress;
	@OneToOne
	private User author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;
	
	public Reply() {
		super();
	}

	private Reply(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.content = builder.content;
		this.hostAddress = builder.hostAddress;
		this.author = builder.author;
		this.comment = builder.comment;
	}  
	
	public Long getId() {
		return id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public String getContent() {
		return content;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public static Reply convert(ReplyDTO replyDTO) {
		return new Reply.Builder()
				.id(replyDTO.getId())
				.createAt(replyDTO.getCreateAt())
				.content(replyDTO.getContent())
				.hostAddress(replyDTO.getHostAddress())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private String content;
		private String hostAddress;
		private User author;
		private Comment comment;
		
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

		public Builder hostAddress(String hostAddress) {
			this.hostAddress = hostAddress;
			return this;
		}

		public Builder author(User author) {
			this.author = author;
			return this;
		}
		
		public Builder comment(Comment comment) {
			this.comment = comment;
			return this;
		}

		public Reply build() {
			return new Reply(this);
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
		Reply other = (Reply) obj;
		return Objects.equals(id, other.id);
	}
}
