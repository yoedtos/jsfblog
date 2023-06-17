package net.yoedtos.blog.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.yoedtos.blog.util.Constants;

public class DaoHandler {
	private static EntityManagerFactory factory;
	
	private DaoHandler() {}
	
	public static void init() {
		factory = Persistence.createEntityManagerFactory(Constants.PERSIST_UNIT);
	}
	
	public static void shutdown() {
		factory.close();
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}	
}
