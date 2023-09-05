package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Message;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class MessageDao extends AbstractDao<Message> implements Repository<Message>{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageDao.class);
	
	public MessageDao() {
		super(Message.class);
	}

	@Override
	public void persist(Message message) throws DaoException {
		try {
			save(message);
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
	public Message merge(Message message) throws DaoException {
		Message messageDb;
		try {
			 messageDb = update(message);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return messageDb;
	}

	@Override
	public Message findById(Long id) throws DaoException {
		Message messageDb;
		try {
			messageDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return messageDb;
	}

	@Override
	public List<Message> findAll() throws DaoException {
		List<Message> messages;
		try {
			messages = loadByQuery("Message.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return messages;
	}
}
