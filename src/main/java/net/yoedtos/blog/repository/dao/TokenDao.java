package net.yoedtos.blog.repository.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Token;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class TokenDao extends AbstractDao<Token> implements Repository<Token> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenDao.class);
	
	public TokenDao() {
		super(Token.class);
	}

	@Override
	public void persist(Token token) throws DaoException {
		try {
			save(token);
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
	public Token merge(Token token) throws DaoException {
		Token tokenDb;
		try {
			 tokenDb = update(token);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return tokenDb;
	}

	@Override
	public Token findById(Long id) throws DaoException {
		Token token;
		try {
			token = loadById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return token;
	}

	@Override
	public List<Token> findAll() throws DaoException {
		List<Token> tokens;
		try {
			tokens = loadByQuery("Token.loadAll");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return tokens;
	}

	public Token findByUsername(String creator) throws DaoException {
		Token token;
		try {
			token = loadOneByQuery("Token.findByUsername", QueryKey.USERNAME, creator);
		} catch (NoResultException e) {
			LOGGER.error(e.getMessage());
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DaoException(e.getMessage());
		}
		return token;
	}
}
