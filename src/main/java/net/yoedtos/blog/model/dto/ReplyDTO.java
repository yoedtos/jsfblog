package net.yoedtos.blog.model.dto;

import java.util.Date;

import net.yoedtos.blog.model.entity.Reply;

public class ReplyDTO {
	
	private Long id;
	private Long commentId;
	private String author;
	private Date createAt;
	private String content;
	private String hostAddress;

	private ReplyDTO(Builder builder) {
		super();
		this.id = builder.id;
		this.commentId = builder.commentId;
		this.author = builder.author;
		this.createAt = builder.createAt;
		this.content = builder.content;
		this.hostAddress = builder.hostAddress;
	}   
	
	public Long getId() {
		return id;
	}

	public Long getCommentId() {
		return commentId;
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
	
	public String getHostAddress() {
		return hostAddress;
	}
	
	public static ReplyDTO convert(Reply reply) {
		return new ReplyDTO.Builder(reply.getAuthor().getUsername())
				.replyId(reply.getId())
				.commentId(reply.getComment().getId())
				.createAt(reply.getCreateAt())
				.content(reply.getContent())
				.hostAddress(reply.getHostAddress())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Long commentId;
		private String author;
		private Date createAt;
		private String content;
		private String hostAddress;
		
		public Builder(String author) {
			this.author = author;
		}
		
		public Builder replyId(Long id) {
			this.id = id;
			return this;
		}

		public Builder commentId(Long commentId) {
			this.commentId = commentId;
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

		public Builder hostAddress(String hostAddress) {
			this.hostAddress = hostAddress;
			return this;
		}

		public ReplyDTO build() {
			return new ReplyDTO(this);
		}
	}
}
