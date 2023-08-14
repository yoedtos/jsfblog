package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class CommentDao extends AbstractDao<Comment> implements Repository<Comment> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentDao.class);
	
	public CommentDao() {
		super(Comment.class);
	}

	@Override
	public void persist(Comment comment) throws DaoException {
		try {
			save(comment);
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
	public Comment merge(Comment comment) throws DaoException {
		Comment commentDb;
		try {
			 commentDb = update(comment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return commentDb;
	}

	@Override
	public Comment findById(Long id) throws DaoException {
		Comment commentDb;
		try {
			commentDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return commentDb;
	}

	@Override
	public List<Comment> findAll() throws DaoException {
		List<Comment> comments;
		try {
			comments = loadByQuery("Comment.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return comments;
	}

	public List<Comment> findAllByPostId(Long id) throws DaoException {
		return null;
	}
}
