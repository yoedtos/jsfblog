package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Remember;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class RememberDao extends AbstractDao<Remember> implements Repository<Remember> {

	public RememberDao() {
		super(Remember.class);
	}

	@Override
	public void persist(Remember t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Remember merge(Remember t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Remember findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Remember> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Remember findByUsername(String user) throws DaoException {
		return null;
	}
}
