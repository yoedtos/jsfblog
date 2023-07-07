package net.yoedtos.blog.model.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import net.yoedtos.blog.model.Type;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.util.Constants;

public class MediaDTO {

	private Long id;
	private Date createAt;
	private String owner;
	private String name;
	private String description;
	private String contentType;
	private Type type;
	private String urn;
	private byte[] binary;

	public MediaDTO(Builder builder) {
		super();
		this.id = builder.id;
		this.createAt = builder.createAt;
		this.owner = builder.owner;
		this.name = builder.name;
		this.description = builder.description;
		this.contentType = builder.contentType;
		this.type = builder.type;
		this.urn = builder.urn;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public byte[] getBinary() {
		return binary;
	}

	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

	public static MediaDTO convert(Media media) {
		return new MediaDTO.Builder()
				.id(media.getId())
				.createAt(media.getCreateAt())
				.owner(media.getOwner().getUsername())
				.name(media.getName())
				.description(media.getDescription())
				.contentType(media.getContentType())
				.type(extractType(media.getUrn()))
				.urn("/" + media.getId() + "-" + media.getName())
				.build();
	}
	
	private static Type extractType(String urn) {
		String[] array = urn.split("/");
		Type type;
		switch (array[1]) {
			case Constants.DOCS:
				type = Type.DOC;
				break;
			case Constants.VIDEOS:
				type = Type.VID;
				break;
			case Constants.IMAGES:
				type = Type.IMG;
				break;
			default:
				type = Type.ZIP;
				break;
			}
		
		return type;
	}
	
	public static class Builder {
		private Long id;
		private Date createAt;
		private String owner;
		private String name;
		private String description;
		private String contentType;
		private Type type;
		private String urn;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder createAt(Date createAt) {
			this.createAt = createAt;
			return this;
		}

		public Builder owner(String owner) {
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

		public Builder type(Type type) {
			this.type = type;
			return this;
		}
		
		public Builder urn(String urn) {
			this.urn = urn;
			return this;
		}

		public MediaDTO build() {
			return new MediaDTO(this);
		}
	}

	@Override
	public String toString() {
		return "MediaDTO [id=" + id + ", createAt=" + createAt + ", owner=" + owner + ", name=" + name
				+ ", description=" + description + ", contentType=" + contentType + ", type=" + type + ", urn=" + urn
				+ ", binary=" + Arrays.toString(binary) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(binary);
		result = prime * result + Objects.hash(contentType, createAt, description, id, name, owner, type, urn);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaDTO other = (MediaDTO) obj;
		return Arrays.equals(binary, other.binary) && Objects.equals(contentType, other.contentType)
				&& Objects.equals(createAt, other.createAt) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(owner, other.owner) && type == other.type && Objects.equals(urn, other.urn);
	}
}
