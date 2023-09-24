package net.yoedtos.blog.repository.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Remember;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class RememberDao extends AbstractDao<Remember> implements Repository<Remember> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RememberDao.class);
	
	public RememberDao() {
		super(Remember.class);
	}

	@Override
	public void persist(Remember remember) throws DaoException {
		try {
			save(remember);
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
	public Remember merge(Remember remember) throws DaoException {
		try {
			return update(remember);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public Remember findById(Long id) throws DaoException {
		try {
			return loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}

	@Override
	public List<Remember> findAll() throws DaoException {
		try {
			return loadByQuery("Remember.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
	}
	
	public Remember findByUsername(String user) throws DaoException {
		Remember remember;
		try {
			 remember = loadOneByQuery("Remember.findByUsername", QueryKey.USERNAME, user);
		} catch (NoResultException e) {
			LOGGER.error(e.getMessage());
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return remember;
	}
}
