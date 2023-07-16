package net.yoedtos.blog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AuthException;
import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.util.Constants;

public class AuthService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
	
	private UserDao userDao;
	private CryptoHelper cryptoHelper;
	
	public AuthService() {
		this.userDao = RepositoryFactory.create(UserDao.class);
		this.cryptoHelper = new CryptoHelper();
	}
	
	public UserDTO login(Login login) throws ServiceException, AuthException {
		User user;
		try {
			user = userDao.findByUsername(login.getUsername());
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		if(Boolean.FALSE.equals(user.getActive())) {
			LOGGER.error(Constants.USER_INACTIVE);
			throw new AuthException(Constants.USER_INACTIVE);
		}
		
		if (!cryptoHelper.isValidPassword(login.getPassword(), user.getPassword())) {
			LOGGER.error(Constants.USER_PASS_NG);
			throw new AuthException(Constants.USER_PASS_NG);
		}
		
		return UserDTO.convert(user);
	}
}
