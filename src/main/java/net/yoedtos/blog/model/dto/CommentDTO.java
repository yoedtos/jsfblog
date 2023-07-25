package net.yoedtos.blog.model.dto;

import java.util.Date;

import net.yoedtos.blog.model.entity.Comment;

public class CommentDTO {
	
	private Long id;
	private Long postId;
	private String author;
	private Date createAt;
	private String content;
	private String email;
	private String hostAddress;

	private CommentDTO(Builder builder) {
		super();
		this.id = builder.id;
		this.postId = builder.postId;
		this.author = builder.author;
		this.createAt = builder.createAt;
		this.content = builder.content;
		this.email = builder.email;
		this.hostAddress = builder.hostAddress;
	}   
	
	public Long getId() {
		return id;
	}

	public Long getPostId() {
		return postId;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getContent() {
		return content;
	}

	public String getEmail() {
		return email;
	}
	
	public String getHostAddress() {
		return hostAddress;
	}
	
	public static CommentDTO convert(Comment comment) {
		return new CommentDTO.Builder(comment.getAuthor())
				.commentId(comment.getId())
				.postId(comment.getPost().getId())
				.createAt(comment.getCreateAt())
				.content(comment.getContent())
				.email(comment.getEmail())
				.hostAddress(comment.getHostAddress())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Long postId;
		private String author;
		private Date createAt;
		private String content;
		private String email;
		private String hostAddress;
		
		public Builder(String author) {
			this.author = author;
		}
		
		public Builder commentId(Long id) {
			this.id = id;
			return this;
		}

		public Builder postId(Long postId) {
			this.postId = postId;
			return this;
		}
		
		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
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

		public CommentDTO build() {
			return new CommentDTO(this);
		}
	}
}
