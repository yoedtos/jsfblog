package net.yoedtos.blog.model.dto;

import java.util.List;

public class Page {

	private String pageId;
	private String first;
	private String last;
	private String next;
	private String previous;
	private String current;
	private List<PostDTO> posts;
	
	private Page(Builder builder) {
		this.next = builder.next;
		this.previous = builder.previous;
		this.current = builder.current;
		this.posts = builder.posts;
	}
	
	public String getPageId() {
		return pageId;
	}
	
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	public String getFirst() {
		return first;
	}
	
	public void setFirst(String first) {
		this.first = first;
	}
	
	public String getLast() {
		return last;
	}
	
	public void setLast(String last) {
		this.last = last;
	}
	
	public String getNext() {
		return next;
	}
	
	public void setNext(String next) {
		this.next = next;
	}
	
	public String getPrevious() {
		return previous;
	}
	
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	
	public String getCurrent() {
		return current;
	}
	
	public void setCurrent(String current) {
		this.current = current;
	}

	public List<PostDTO> getPosts() {
		return posts;
	}
	
	public static class Builder {
		private String next;
		private String previous;
		private String current;
		private List<PostDTO> posts;
				
		public Builder next(String next) {
			this.next = next;
			return this;
		}
		
		public Builder previous(String previous) {
			this.previous = previous;
			return this;
		}
		
		public Builder current(String current) {
			this.current = current;
			return this;
		}
		
		public Builder posts(List<PostDTO> posts) {
			this.posts = posts;
			return this;
		}
		
		public Page build() {
			return new Page(this);
		}
	}
}
