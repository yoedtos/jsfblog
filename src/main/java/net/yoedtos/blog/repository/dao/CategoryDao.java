package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class CategoryDao extends AbstractDao<Category> implements Repository<Category>{
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDao.class);
	
	public CategoryDao() {
		super(Category.class);
	}

	@Override
	public void persist(Category category) throws DaoException {
		
		try {
			save(category);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public void remove(Long id) throws DaoException {
		try {
			delete(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}	
	}
	
	@Override
	public Category merge(Category category) throws DaoException {
		Category categoryDb;
		try {
			 categoryDb = update(category);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return categoryDb;
	}
	
	@Override
	public Category findById(Long id) throws DaoException {
		Category categoryDb;
		try {
			categoryDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return categoryDb;
	}

	@Override
	public List<Category> findAll() throws DaoException {	
		List<Category> categories;
		try {
			categories = loadByQuery("Category.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return categories;
	}
}
