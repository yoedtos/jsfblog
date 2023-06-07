package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class UserDao extends AbstractDao<User> implements Repository<User> {

	@Override
	public void persist(User t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User merge(User t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
