package net.yoedtos.blog.model.dto;

import java.util.Date;

import net.yoedtos.blog.model.entity.Post;

public class PostDTO {
	private Long id;
	private Date createdAt;
	private String title;
	private String author;
	private Long categoryId;
	private String intro;
	private String content;
	private String metaDesc;
	private String metaKey;

	private PostDTO(Builder builder) {
		super();
		this.id = builder.id;
		this.createdAt = builder.createdAt;
		this.title = builder.title;
		this.author = builder.author;
		this.categoryId = builder.categoryId;
		this.intro = builder.intro;
		this.content = builder.content;
		this.metaDesc = builder.metaDesc;
		this.metaKey = builder.metaKey;
	}
	
	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}   

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public String getIntro() {
		return intro;
	}
	
	public String getContent() {
		return this.content;
	}

	public String getMetaDesc() {
		return metaDesc;
	}

	public String getMetaKey() {
		return metaKey;
	}
	
	public static PostDTO convert(Post post) {
		return new PostDTO.Builder(post.getAuthor().getUsername())
				.postId(post.getId())
				.createdAt(post.getCreatedAt())
				.title(post.getTitle())
				.categoryId(post.getCategory().getId())
				.intro(post.getIntro())
				.content(post.getContent())
				.metaDesc(post.getMetaDesc())
				.metaKey(post.getMetaKey())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Date createdAt;
		private String title;
		private String author;
		private Long categoryId;
		private String intro;
		private String content;
		private String metaDesc;
		private String metaKey;
		
		public Builder(String author) {
			this.author = author;
		}
		
		public Builder postId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder createdAt(Date date) {
			this.createdAt = date;
			return this;
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder categoryId(Long categoryId) {
			this.categoryId = categoryId;
			return this;
		}
		
		public Builder intro(String intro) {
			this.intro = intro;
			return this;
		}
		
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		public Builder metaDesc(String metaDesc) {
			this.metaDesc = metaDesc;
			return this;
		}
		
		public Builder metaKey(String metaKey) {
			this.metaKey = metaKey;
			return this;
		}
		
		public PostDTO build() {
			return new PostDTO(this);
		}
	}
}
