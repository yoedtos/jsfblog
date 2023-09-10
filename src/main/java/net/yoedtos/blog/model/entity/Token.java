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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@SuppressWarnings("serial")
/**
 * Entity implementation class for Entity: Token
 *
 */
@Entity
public class Token implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "token_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createAt;
	@Column(length = 36)
	private String value;
	@OneToOne
	private User creator;
	
	public Token() {}

	public Token(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.value = builder.value;
		this.creator = builder.creator;
	}
	
	public Long getId() {
		return id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public User getCreator() {
		return creator;
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private String value;
		private User creator;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}
		
		public Builder value(String value) {
			this.value = value;
			return this;
		}
		
		public Builder creator(User creator) {
			this.creator = creator;
			return this;
		}
		
		public Token build() {
			return new Token(this);
		}
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", createAt=" + createAt + ", value=" + value + ", creator=" + creator + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createAt, creator, id, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(createAt.getTime()/1000, other.createAt.getTime()/1000) && Objects.equals(creator, other.creator)
				&& Objects.equals(id, other.id) && Objects.equals(value, other.value);
	}
}
