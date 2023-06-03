package net.yoedtos.blog.model.dto;

import net.yoedtos.blog.model.entity.Category;

public class CategoryDTO {
	private Long id;
	private String value;
	
	public CategoryDTO(String category) {
		this.value = category;
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
	
	public static CategoryDTO convert(Category category) {
		CategoryDTO categoryDTO = new CategoryDTO(category.getValue());
		categoryDTO.setId(category.getId());
		return categoryDTO;
	}
}
