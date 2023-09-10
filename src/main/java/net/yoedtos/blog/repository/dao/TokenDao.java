package net.yoedtos.blog.repository.dao;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.model.entity.Token;
import net.yoedtos.blog.repository.Repository;

@SuppressWarnings("serial")
public class TokenDao extends AbstractDao<Token> implements Repository<Token> {

	public TokenDao() {
		super(Token.class);
	}

	@Override
	public void persist(Token t) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Token merge(Token t) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Token findById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Token> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	public Token findByUsername(String usernameOne) {
		// TODO Auto-generated method stub
		return null;
	}
}
