package net.yoedtos.blog.repository.dao;

public enum QueryKey {
	USERNAME("user"),
	ID("id");
	
	private String value;
	
	QueryKey(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
