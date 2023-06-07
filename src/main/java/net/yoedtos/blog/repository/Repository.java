package net.yoedtos.blog.repository;

import java.util.List;

import net.yoedtos.blog.exception.DaoException;

public interface Repository<T> {

	void persist(T t) throws DaoException;
	void remove(Long id) throws DaoException;
	T merge(T t) throws DaoException;
	T findById(Long id) throws DaoException;
	List<T> findAll() throws DaoException;
	
}
