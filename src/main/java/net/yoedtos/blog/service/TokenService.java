package net.yoedtos.blog.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.Reset;
import net.yoedtos.blog.model.entity.Token;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.TokenDao;
import net.yoedtos.blog.repository.dao.UserDao;

public class TokenService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
	
	private UserDao userDao;
	private TokenDao tokenDao;
	private TokenHelper tokenHelper;
	
	public TokenService() {
		userDao = RepositoryFactory.create(UserDao.class);
		tokenDao = RepositoryFactory.create(TokenDao.class);
		tokenHelper = new TokenHelper();
	}
	
	public Token make(Reset reset) throws ServiceException {
		Token token;
		try {
			User user = userDao.findByUsername(reset.getUsername());
			Token tokenDb = tokenDao.findByUsername(user.getUsername());
			if(tokenDb != null) {
				tokenDb.setCreateAt(new Date());
				tokenDb.setValue(tokenHelper.generate());
				token = tokenDao.merge(tokenDb);
			} else {
				token = new Token.Builder()
						.createAt(new Date())
						.value(tokenHelper.generate())
						.creator(user)
						.build();
				tokenDao.persist(token);
			}
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return token;
	}

	public void expire(Reset reset) throws ServiceException {
		try {
			tokenHelper.validate(reset.getToken());
			Token token = tokenDao.findByUsername(reset.getUsername());
			if(!token.getValue().equals(reset.getToken())) {
				LOGGER.error("Invalid token!");
				throw new ServiceException("Invalid token!");
			}
			token.getCreator().setPassword(reset.getCodedpass());
			tokenDao.merge(token);
			tokenDao.remove(token.getId());
		} catch (IllegalArgumentException | DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
}
