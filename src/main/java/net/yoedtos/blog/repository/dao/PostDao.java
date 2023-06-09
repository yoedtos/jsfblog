package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class PostDao extends AbstractDao<Post> implements Repository<Post> {

	@Override
	public void persist(Post t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Post merge(Post t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
