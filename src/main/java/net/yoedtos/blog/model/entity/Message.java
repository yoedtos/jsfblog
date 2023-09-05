package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import net.yoedtos.blog.model.dto.MessageDTO;

/**
 * Entity implementation class for Entity: Message
 *
 */
@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "message_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createAt;
	private String senderName;
	private String senderEmail;
	@Column(length = 50)
	private String subject;
	@Column(length = 300)
	private String content;
	private String hostAddress;
	
	public Message() {}
	
	public Message(Builder builder) {
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
	
	public void setId(Long id) {
		this.id = id;
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
	
	public static Message convert(MessageDTO noticeDTO) {
		return new Message.Builder()
				.id(noticeDTO.getId())
				.createAt(noticeDTO.getCreateAt())
				.senderName(noticeDTO.getSenderName())
				.senderEmail(noticeDTO.getSenderEmail())
				.subject(noticeDTO.getSubject())
				.content(noticeDTO.getContent())
				.hostAddress(noticeDTO.getHostAddress())
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
		
		public Message build() {
			return new Message(this);
		}
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", createAt=" + createAt + ", senderName=" + senderName + ", senderEmail="
				+ senderEmail + ", subject=" + subject + ", content=" + content + ", hostAddress=" + hostAddress + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createAt, hostAddress, id, senderEmail, senderName, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return Objects.equals(content, other.content) && Objects.equals(createAt.getTime()/1000, other.createAt.getTime()/1000)
				&& Objects.equals(hostAddress, other.hostAddress) && Objects.equals(id, other.id)
				&& Objects.equals(senderEmail, other.senderEmail) && Objects.equals(senderName, other.senderName)
				&& Objects.equals(subject, other.subject);
	}
}
