package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class MediaDao extends AbstractDao<Media> implements Repository<Media>{

	public MediaDao() {
		super(Media.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void persist(Media t) throws DaoException {
		// TODO Auto-generated method stub
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Media merge(Media t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Media findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Media> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
