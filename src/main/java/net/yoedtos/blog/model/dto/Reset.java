package net.yoedtos.blog.model.dto;

import java.util.Objects;

public final class Reset {

	private String username;
	private String codedpass;
	private String token;
	
	private Reset(Builder builder) {
		this.username = builder.username;
		this.codedpass = builder.codedpass;
		this.token = builder.token;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCodedpass() {
		return codedpass;
	}
	
	public void setCodedpass(String codedpass) {
		this.codedpass = codedpass;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public static class Builder {
		private String username;
		private String codedpass;
		private String token;
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder codedpass(String codedpass) {
			this.codedpass = codedpass;
			return this;
		}		
		
		public Builder token(String token) {
			this.token = token;
			return this;
		}
		
		public Reset build() {
			return new Reset(this);
		}
	}
	
	@Override
	public String toString() {
		return "Reset [username=" + username + ", codedpass=" + codedpass + ", token=" + token + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codedpass, token, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reset other = (Reset) obj;
		return Objects.equals(codedpass, other.codedpass) && Objects.equals(token, other.token)
				&& Objects.equals(username, other.username);
	}
}
