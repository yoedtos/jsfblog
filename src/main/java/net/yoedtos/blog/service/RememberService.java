package net.yoedtos.blog.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AuthException;
import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.RememberData;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.Remember;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.RememberDao;
import net.yoedtos.blog.repository.dao.UserDao;

public class RememberService implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(RememberService.class);
	
	private UserDao userDao;
	private RememberDao rememberDao;
	private CryptoHelper cryptoHelper;
	
	public RememberService() {
		userDao = RepositoryFactory.create(UserDao.class);
		rememberDao = RepositoryFactory.create(RememberDao.class);
		cryptoHelper = new CryptoHelper();
	}
	
	public RememberData set(String username) throws ServiceException {
		RememberData data;
		try {
			String badge = cryptoHelper.random();
			Remember remember = rememberDao.findByUsername(username);
			if (remember == null) {
				User user = userDao.findByUsername(username);
				remember = new Remember.Builder()
								.createAt(new Date())
								.value(badge)
								.user(user)
								.build();
				rememberDao.persist(remember);
			} else {
				remember.setCreateAt(new Date());
				remember.setValue(badge);
				rememberDao.merge(remember);
			}
			
			String index = cryptoHelper.encode(createIndex(remember.getUser()));

			data = new RememberData.Builder()
						.username(null)
						.index(index)
						.badge(remember.getValue())
						.build();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return data;
	}
	
	public UserDTO getIn(RememberData data) throws ServiceException {
		User user;
		try {
			String username = cryptoHelper.decode(data.getIndex()).split("\\|")[1];
			Remember remember = rememberDao.findByUsername(username);
			if(!remember.getValue().equals(data.getBadge())) {
				throw new AuthException("Failed to authenticate!");
			}
			user = remember.getUser();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return UserDTO.convert(user);
	}
	
	public void unset(String username) throws ServiceException {
		try {
			Remember remember = rememberDao.findByUsername(username);
			rememberDao.remove(remember.getId());
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	private String createIndex(User user) {
		return new StringBuilder()
				.append(new Date().toInstant().getEpochSecond())
				.append("|").append(user.getUsername()).toString();
	}
}
