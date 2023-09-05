package net.yoedtos.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.dto.MessageDTO;
import net.yoedtos.blog.model.entity.Message;
import net.yoedtos.blog.repository.dao.MessageDao;

public class MessageService extends AbstractService<MessageDTO> implements Service{
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
	
	private MessageDao messageDao;
	
	public MessageService() {
		messageDao = RepositoryFactory.create(MessageDao.class);
	}
	
	@Override
	public void create(MessageDTO messageDto) throws ServiceException {
		messageDto.setCreateAt(getToday());
		try {
			Message message = Message.convert(messageDto);
			messageDao.persist(message);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void remove(Long id) throws ServiceException {
		try {
			messageDao.remove(id);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public MessageDTO update(MessageDTO t) throws ServiceException {
		return null;
	}

	@Override
	public MessageDTO get(Long id) throws ServiceException {
		return null;
	}

	@Override
	public List<MessageDTO> getAll() throws ServiceException {
		List<Message> messages;
		try {
			messages = messageDao.findAll();
		} catch (DaoException e) {
			LOGGER.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return convertToDto(messages);
	}

	private List<MessageDTO> convertToDto(List<Message> messages) {
		return messages.stream()
				.map(MessageDTO::convert)
				.collect(Collectors.toList());
	}
}
