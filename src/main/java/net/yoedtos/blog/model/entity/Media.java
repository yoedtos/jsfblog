package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import net.yoedtos.blog.model.dto.MediaDTO;

/**
 * Entity implementation class for Entity: Media
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQueries({ @NamedQuery(name = "Media.loadAll", query = "SELECT m FROM Media m") })
public class Media implements Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "media_id")
	private Long id;
	@Temporal(TIMESTAMP)
	private Date createAt;
	@OneToOne
	private User owner;
	@Column(length = 20)
	private String name;
	@Column(length = 60)
	private String description;
	private String contentType;
	private String urn;

	public Media() {
		super();
	}

	private Media(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.owner = builder.owner;
		this.name = builder.name;
		this.description = builder.description;
		this.contentType = builder.contentType;
		this.urn = builder.urn;
	}

	public Long getId() {
		return id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getContentType() {
		return contentType;
	}

	public String getUrn() {
		return urn;
	}
	
	public void setUrn(String urn) {
		this.urn = urn;
	}

	public static Media convert(MediaDTO mediaDTO) {
		return new Media.Builder()
				.id(mediaDTO.getId())
				.createAt(mediaDTO.getCreateAt())
				.name(mediaDTO.getName())
				.description(mediaDTO.getDescription())
				.contentType(mediaDTO.getContentType())
				.urn(mediaDTO.getUrn())
				.build();
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private User owner;
		private String name;
		private String description;
		private String contentType;
		private String urn;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder owner(User owner) {
			this.owner = owner;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder urn(String urn) {
			this.urn = urn;
			return this;
		}

		public Media build() {
			return new Media(this);
		}
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", createAt=" + createAt + ", owner=" + owner + ", name=" + name + ", description="
				+ description + ", contentType=" + contentType + ", urn=" + urn + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contentType, createAt, description, id, name, owner, urn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		return Objects.equals(contentType, other.contentType) && Objects.equals(createAt, other.createAt)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(owner, other.owner)
				&& Objects.equals(urn, other.urn);
	}
}

