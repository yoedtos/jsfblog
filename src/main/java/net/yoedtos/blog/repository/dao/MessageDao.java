package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Message;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class MessageDao extends AbstractDao<Message> implements Repository<Message>{

	public MessageDao() {
		super(Message.class);
	}

	@Override
	public void persist(Message t) throws DaoException {
		// TODO Auto-generated method stub
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
	}

	@Override
	public Message merge(Message t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}
}
