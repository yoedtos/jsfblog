package net.yoedtos.blog.model.dto;

import java.util.Date;
import java.util.Objects;

import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.util.Constants;

public final class UserDTO {
	
	private Long id;
	private String userName;
	private String fullName;
	private String email;
	private String password;
	private boolean active;
	private Role role;
	private String hostAddress;
	private Date createdAt;
	
	private UserDTO(Builder builder) {
		this.id = builder.id;
		this.userName = builder.username;
		this.fullName = builder.fullname;
		this.email = builder.email;
		this.password = builder.password;
		this.active = builder.active;
		this.role = builder.role;
		this.hostAddress = builder.hostAddress;
		this.createdAt = builder.createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getFullname() {
		return fullName;
	}

	public void setFullname(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getHostAddress() {
		return hostAddress;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public static UserDTO convert(User user) {
		return new UserDTO.Builder(user.getUsername())
				.id(user.getUserId())
				.fullname(user.getFullname())
				.email(user.getEmail())
				.password(Constants.PASS_MASK)
				.hostAddress(user.getHostAddress())
				.role(user.getRole())
				.active(user.getActive())
				.createdAt(user.getCreateAt())
				.build(); 
	}

	public static class Builder {
		private Long id;
		private String username;
		private String fullname;
		private String email;
		private String password;
		private String hostAddress;
		private Role role;
		private boolean active;
		private Date createdAt;
		
		public Builder(String username) {
			this.username = username;
		}
		
		public Builder id(Long id) {
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
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder active(Boolean active) {
			this.active = active;
			return this;
		}
		
		public Builder role(Role role) {
			this.role = role;
			return this;
		}
		
		public Builder hostAddress(String hostAddress) {
			this.hostAddress = hostAddress;
			return this;
		}
		
		public Builder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}
		
		public UserDTO build() {
			return new UserDTO(this);
		}
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", userName=" + userName + ", fullName=" + fullName + ", email=" + email
				+ ", active=" + active + ", role=" + role + ", hostAddress=" + hostAddress + ", createdAt=" + createdAt
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, createdAt, email, fullName, hostAddress, id, role, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return active == other.active && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(hostAddress, other.hostAddress) && Objects.equals(id, other.id) && role == other.role
				&& Objects.equals(userName, other.userName);
	}
}
