package net.yoedtos.blog.model.dto;

import java.util.Objects;

public class RememberData {

	private String username;
	private String index;
	private String badge;
	
	private RememberData(Builder builder) {
		this.username = builder.username;
		this.index = builder.index;
		this.badge = builder.badge;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}
	
	public static class Builder {
		private String username;
		private String index;
		private String badge;
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder index(String index) {
			this.index = index;
			return this;
		}		
		
		public Builder badge(String badge) {
			this.badge = badge;
			return this;
		}
		
		public RememberData build() {
			return new RememberData(this);
		}
	}

	@Override
	public String toString() {
		return "RememberData [username=" + username + ", index=" + index + ", badge=" + badge + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(badge, index, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RememberData other = (RememberData) obj;
		return Objects.equals(badge, other.badge) && Objects.equals(index, other.index)
				&& Objects.equals(username, other.username);
	}
}
