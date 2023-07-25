package net.yoedtos.blog.model.dto;

import java.util.Date;
import java.util.Objects;

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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", createdAt=" + createdAt + ", title=" + title + ", author=" + author
				+ ", categoryId=" + categoryId + ", intro=" + intro + ", content=" + content + ", metaDesc=" + metaDesc
				+ ", metaKey=" + metaKey + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, categoryId, content, createdAt, id, intro, metaDesc, metaKey, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostDTO other = (PostDTO) obj;
		return Objects.equals(author, other.author) && Objects.equals(categoryId, other.categoryId)
				&& Objects.equals(content, other.content) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && Objects.equals(intro, other.intro)
				&& Objects.equals(metaDesc, other.metaDesc) && Objects.equals(metaKey, other.metaKey)
				&& Objects.equals(title, other.title);
	}
}
