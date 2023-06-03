package net.yoedtos.blog.service;

import java.util.Date;
import java.util.List;

import net.yoedtos.blog.exception.ServiceException;

public abstract class AbstractService<T> {
	
	protected Date getToday() {
		return new Date();
	}
	
	public abstract void create(T t) throws ServiceException;
	public abstract void remove(Long id) throws ServiceException;
	public abstract T update(T t) throws ServiceException;
	public abstract T get(Long id) throws ServiceException;
	public abstract List<T> getAll() throws ServiceException;
}
