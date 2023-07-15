package net.yoedtos.blog.repository.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

@SuppressWarnings("serial")
public abstract class AbstractDao<T> implements Serializable {

	private EntityManager entityManager;
	private EntityTransaction transaction;
	private Class<T> clazz;

	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected void save(T entity) throws Exception {
		try {
			begin();
			entityManager.persist(entity);
			end();
		} catch (Exception e) {
			cancel();
			throw e;
		}
	}

	protected void delete(Long id) throws Exception {
		try {
			begin();
			T t = entityManager.find(clazz, id);
			T entityToRemove = entityManager.merge(t);
			entityManager.remove(entityToRemove);
			end();
		} catch (Exception e) {
			cancel();
			throw e;
		}
	}

	protected T update(T entity) throws Exception {
		T t;
		try {
			begin();
			t = entityManager.merge(entity);
			end();
		} catch (Exception e) {
			cancel();
			throw e;
		}
		return t;
	}

	protected T loadById(Long id) throws Exception {
		T t;
		try {
			begin();
			t = entityManager.find(clazz, id);
			end();
		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	protected List<T> loadByQuery(String namedQuery) throws Exception {
		EntityManager manager = DaoHandler.getEntityManager();
		
		List<T> listT;
		try {
			Query query = manager.createNamedQuery(namedQuery);
			listT = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			manager.close();
		}
		return listT;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> loadByQuery(String namedQuery, QueryKey key, Object param) throws Exception {
		EntityManager manager = DaoHandler.getEntityManager();
		
		List<T> listT;
		try {
			Query query = manager.createNamedQuery(namedQuery);
			query.setParameter(key.getValue(), param);
			listT = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			manager.close();
		}
		return listT;
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> loadByQuery(String namedQuery, long start, int max) throws Exception {
		EntityManager manager = DaoHandler.getEntityManager();
		
		List<T> listT;
		try {
			Query query = manager.createNamedQuery(namedQuery);
			query.setFirstResult((int) start);
			query.setMaxResults(max);
			listT = query.getResultList();
		} catch (Exception e) {
			throw e;
		} finally {
			manager.close();
		}
		return listT;
	}
	
	protected Object loadOneByQuery(String namedQuery) throws Exception {
		EntityManager manager = DaoHandler.getEntityManager();
		Object obj;
		try {
			Query query = manager.createNamedQuery(namedQuery);
			obj = query.getSingleResult();
		} catch (Exception e) {
			throw e;
		} finally {
			manager.close();
		}
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	protected T loadOneByQuery(String namedQuery, QueryKey key, String param) throws Exception {
		EntityManager manager = DaoHandler.getEntityManager();
		T t;
		try {
			Query query = manager.createNamedQuery(namedQuery);
			query.setParameter(key.getValue(), param);
			t = (T) query.getSingleResult();
		} catch (Exception e) {
			throw e;
		} finally {
			manager.close();
		}
		return t;
	}

	private void begin() {
		this.entityManager = DaoHandler.getEntityManager();
		transaction = entityManager.getTransaction();
		transaction.begin();
	}

	private void end() {
		transaction.commit();
		entityManager.close();
	}

	private void cancel() {
		transaction.rollback();
		entityManager.close();
	}
}
