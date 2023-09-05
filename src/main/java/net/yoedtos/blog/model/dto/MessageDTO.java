package net.yoedtos.blog.model.dto;

import java.util.Date;

import net.yoedtos.blog.model.entity.Message;

public class MessageDTO {
	
	private Long id;
	private Date createAt;
	private String senderName;
	private String senderEmail;
	private String subject;
	private String content;
	private String hostAddress;
	
	public MessageDTO(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.senderName = builder.senderName;
		this.senderEmail = builder.senderEmail;
		this.subject = builder.subject;
		this.content = builder.content;
		this.hostAddress = builder.hostAddress;
	}

	public Long getId() {
		return id;
	}
	
	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getHostAddress() {
		return hostAddress;
	}
	
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	
	public static MessageDTO convert(Message notice) {
		return new MessageDTO.Builder()
				.id(notice.getId())
				.createAt(notice.getCreateAt())
				.senderName(notice.getSenderName())
				.senderEmail(notice.getSenderEmail())
				.subject(notice.getSubject())
				.content(notice.getContent())
				.hostAddress(notice.getHostAddress())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private String senderName;
		private String senderEmail;
		private String subject;
		private String content;
		private String hostAddress;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder senderName(String senderName) {
			this.senderName = senderName;
			return this;
		}

		public Builder senderEmail(String senderEmail) {
			this.senderEmail = senderEmail;
			return this;
		}

		public Builder subject(String subject) {
			this.subject = subject;
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
		
		public MessageDTO build() {
			return new MessageDTO(this);
		}
	}
}
