package net.yoedtos.blog.model;

public enum Type {
	DOC("document"),
	IMG("image"),
	VID("video"),
	ZIP("zipped");
	
	private Type(String type) {
		this.type = type;
	}
	
	private String type;
	
	public String getType() {
		return type;
	}
}
