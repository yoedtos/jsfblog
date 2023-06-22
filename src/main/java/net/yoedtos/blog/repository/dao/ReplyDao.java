package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class ReplyDao extends AbstractDao<Reply> implements Repository<Reply> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyDao.class);
	
	public ReplyDao() {
		super(Reply.class);
	}

	@Override
	public void persist(Reply reply) throws DaoException {
		try {
			save(reply);
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
	public Reply merge(Reply reply) throws DaoException {
		Reply replyDb;
		try {
			 replyDb = update(reply);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return replyDb;
	}

	@Override
	public Reply findById(Long id) throws DaoException {
		Reply replyDb;
		try {
			replyDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return replyDb;
	}

	@Override
	public List<Reply> findAll() throws DaoException {
		List<Reply> replies;
		try {
			replies = loadByQuery("Reply.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return replies;
	}

	public List<Reply> findAllByCommentId(Long id) throws DaoException {
		List<Reply> replies;
		try {
			replies = loadByQuery("Reply.findAllByCommentId", QueryKey.COMMENT_ID, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return replies;
	}
}
