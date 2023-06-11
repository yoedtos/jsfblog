package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class CommentDao extends AbstractDao<Comment> implements Repository<Comment> {

	@Override
	public void persist(Comment t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Comment merge(Comment t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
