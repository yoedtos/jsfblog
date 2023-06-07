package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.util.Constants;

public class UserService extends AbstractService<UserDTO> implements Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private UserDao userDao;
	private CryptoHelper encryptor;
	
	public UserService() {
		this.userDao = RepositoryFactory.create(UserDao.class);
		encryptor = new CryptoHelper();
	}

	@Override
	public void create(UserDTO userDto) throws ServiceException {
		User user = User.convert(userDto);
		user.setPassword(encryptor.encrypt(userDto.getPassword()));
		try {
			userDao.persist(user);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public void remove(Long id) throws ServiceException {
		try {
			userDao.remove(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public UserDTO update(UserDTO userDto) throws ServiceException {
		User userDb;
		try {
			User user = userDao.findById(userDto.getId());
			user.setFullname(userDto.getFullname());
			user.setEmail(userDto.getEmail());
			
			if(!userDto.getPassword().equals(Constants.PASS_MASK)) {
				user.setPassword(encryptor.encrypt(userDto.getPassword()));
			}
			
			userDb = userDao.merge(user);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		return UserDTO.convert(userDb);
	}

	@Override
	public UserDTO get(Long id) throws ServiceException {
		UserDTO userDTO;
		try {
			userDTO = UserDTO.convert(userDao.findById(id));
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> getAll() throws ServiceException {
		List<User> users;
		try {
			users = userDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		
		return users.stream()
				.map(UserDTO::convert)
				.collect(Collectors.toList());
	}
}
