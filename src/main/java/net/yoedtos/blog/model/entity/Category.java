package net.yoedtos.blog.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import net.yoedtos.blog.model.dto.CategoryDTO;

/**
 * Entity implementation class for Entity: Category
 *
 */
@SuppressWarnings("serial")
@Entity
@NamedQuery(name="Category.loadAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="category_id")
	private Long id;
	@Column(unique = true, length = 20)
	private String value;

	public Category() {
		super();
	}   
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Category convert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setId(categoryDTO.getId());
		category.setValue(categoryDTO.getValue());
		return category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(value, other.value);
	}
}
