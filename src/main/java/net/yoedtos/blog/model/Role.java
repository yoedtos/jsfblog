package net.yoedtos.blog.model;

public enum Role {
	GUEST(0),
	MEMBER(1),
	MODERATOR(62),
	ADMINISTRATOR(83);
	
	private int value;
	
	Role(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
