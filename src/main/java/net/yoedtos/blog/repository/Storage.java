package net.yoedtos.blog.repository;

import net.yoedtos.blog.exception.FSException;

public interface Storage<T> {

	void store(T t) throws FSException;
	void drop(T t) throws FSException;
	T load(Object obj) throws FSException;
	T replace(T t) throws FSException;
	Object find(Object obj) throws FSException;
}
