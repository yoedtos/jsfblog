package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Constants.CATEGORY_ID;
import static net.yoedtos.blog.control.Constants.CATEGORY_REDIRECT;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.CategoryDTO;
import net.yoedtos.blog.service.CategoryService;

@ManagedBean(name="category")
@RequestScoped
public class CategoryBean extends AbstractBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryBean.class);
	
	private CategoryDTO dto;
	private DataModel<CategoryDTO> categories;
	private CategoryService categoryService;
	
	public CategoryBean() {
		super();
		categoryService = ServiceFactory.create(CategoryService.class);
		dto = new CategoryDTO(null);
	}

	@PostConstruct
	public void loadCategories() {
		List<CategoryDTO> categoriesDto = new ArrayList<>();
		try {
			categoriesDto = categoryService.getAll();
		} catch (ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
		}
		categories = new ListDataModel<>(categoriesDto);
	}
	
	public String save() {
		try {
			categoryService.create(dto);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		return CATEGORY_REDIRECT;
	}

	public String delete() {
		String categoryId = getRequestParam(CATEGORY_ID);
		try {
			categoryService.remove(Long.parseLong(categoryId));
		} catch (NumberFormatException | ServiceException e) {
			LOGGER.error(e.getMessage());
			showErrorMessage(false);
			return "";
		}
		return CATEGORY_REDIRECT;
	}
	
	public void setDto(CategoryDTO dto) {
		this.dto = dto;
	}

	public CategoryDTO getDto() {
		return dto;
	}
	
	public DataModel<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(DataModel<CategoryDTO> categories) {
		this.categories = categories;
	}
}
