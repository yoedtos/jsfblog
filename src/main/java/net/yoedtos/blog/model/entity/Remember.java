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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@SuppressWarnings("serial")
/**
 * Entity implementation class for Entity: Remember
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Remember.loadAll", query="SELECT r FROM Remember r"),
	@NamedQuery(name="Remember.findByUsername", query="SELECT r FROM Remember r WHERE r.user.username = :user")
})
public class Remember implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "remember_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createAt;
	@Column(length = 50)
	private String value;
	@OneToOne
	private User user;
	
	public Remember() {}

	public Remember(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.value = builder.value;
		this.user = builder.user;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private String value;
		private User user;
		
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
		
		public Builder user(User user) {
			this.user = user;
			return this;
		}
		
		public Remember build() {
			return new Remember(this);
		}
	}

	@Override
	public String toString() {
		return "Remember [id=" + id + ", createAt=" + createAt + ", value=" + value + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createAt, id, user, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Remember other = (Remember) obj;
		return Objects.equals(createAt.getTime()/1000, other.createAt.getTime()/1000) && Objects.equals(id, other.id)
				&& Objects.equals(user, other.user) && Objects.equals(value, other.value);
	}
}
