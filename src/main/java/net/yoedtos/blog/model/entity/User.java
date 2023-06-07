package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.UserDTO;

/**
 * Entity implementation class for Entity: User
 *
 */
@SuppressWarnings("serial")
@Entity
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="user_id")
	private Long id;
	@Column(unique = true, length = 20)
	private String username;
	private String password;
	@Column(unique = true, length = 40)
	private String fullname;
	@Column(unique = true, length = 50)
	private String email;
	private String hostAddress;
	@Enumerated(EnumType.STRING)
	private Role role;
	private Boolean active;
	@Temporal(TIMESTAMP)
	private Date createAt;
	
	public User() {}
	
	private User(Builder builder) {
		super();
		this.id = builder.id;
		this.username = builder.username;
		this.fullname = builder.fullname;
		this.email = builder.email;
		this.hostAddress = builder.hostAddress;
		this.role = builder.role;
		this.active = builder.active;
		this.createAt = builder.createAt;
	}
	
	public Long getUserId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostAddress() {
		return hostAddress;
	}
	
	public Role getRole() {
		return this.role;
	}

	public Boolean getActive() {
		return active;
	}
	
	public Date getCreateAt() {
		return createAt;
	}
	
	public static User convert(UserDTO userDTO) {
		return new User.Builder(userDTO.getUsername())
				.userId(userDTO.getId())
				.fullname(userDTO.getFullname())
				.email(userDTO.getEmail())
				.hostAddress(userDTO.getHostAddress())
				.role(userDTO.getRole())
				.active(userDTO.getActive())
				.createAt(userDTO.getCreatedAt())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private String username;
		private String fullname;
		private String email;
		private String hostAddress;
		private Role role;
		private Boolean active;
		private Date createAt;

		public Builder(String username) {
			this.username = username;
		}
		
		public Builder userId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder fullname(String fullname) {
			this.fullname = fullname;
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
		
		public Builder role(Role role) {
			this.role = role;
			return this;
		}

		public Builder active(Boolean active) {
			this.active = active;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}
}
