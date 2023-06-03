package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.CategoryDTO;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.repository.dao.CategoryDao;

public class CategoryService extends AbstractService<CategoryDTO> implements Service{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

	private CategoryDao categoryDao;

	public CategoryService() {
		super();
		categoryDao = RepositoryFactory.create(CategoryDao.class);
	}

	@Override
	public void create(CategoryDTO categoryDto) throws ServiceException {
		Category category = Category.convert(categoryDto);
		
		try {
			categoryDao.persist(category);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public CategoryDTO update(CategoryDTO t) throws ServiceException {
		return null;
	}
	
	@Override
	public void remove(Long id) throws ServiceException {
		try {
			categoryDao.remove(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public CategoryDTO get(Long id) throws ServiceException {
		return null;
	}

	@Override
	public List<CategoryDTO> getAll() throws ServiceException {
		List<Category> categories;
		try {
			categories = categoryDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return categories.stream()
				.map(CategoryDTO::convert)
				.collect(Collectors.toList());
	}	
}
