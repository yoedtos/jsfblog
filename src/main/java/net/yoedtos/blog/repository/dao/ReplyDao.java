package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class ReplyDao extends AbstractDao<Reply> implements Repository<Reply> {

	@Override
	public void persist(Reply t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reply merge(Reply t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reply findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reply> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Reply> findAllByCommentId(long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
