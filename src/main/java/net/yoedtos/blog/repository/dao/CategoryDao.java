package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class CategoryDao extends AbstractDao<Category> implements Repository<Category>{

	@Override
	public void persist(Category category) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
