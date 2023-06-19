package net.yoedtos.blog.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class UserDao extends AbstractDao<User> implements Repository<User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
	
	public UserDao() {
		super(User.class);
	}

	@Override
	public void persist(User user) throws DaoException {
		try {
			save(user);
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
	public User merge(User user) throws DaoException {
		User userDb;
		try {
			 userDb = update(user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return userDb;
	}

	@Override
	public User findById(Long id) throws DaoException {
		User userDb;
		try {
			userDb = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return userDb;
	}

	@Override
	public List<User> findAll() throws DaoException {
		List<User> users;
		try {
			users = loadByQuery("User.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return users;
	}

	public User findByUsername(String author) throws DaoException {
		User user;
		try {
			user = loadOneByQuery("User.findByUsername", QueryKey.USERNAME, author);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return user;
	}
}
