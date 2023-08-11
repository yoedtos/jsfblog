package net.yoedtos.blog.repository.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import net.yoedtos.blog.exception.AppException;
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
	
	public static void createAdminUser() {
		createNativeQuery(new String[] {Constants.SCRIPT_ADMIN});
	}
	
	public static void createDemoData() {
		try (InputStream scriptStream = new FileInputStream(new File(Constants.DEMO_DATA));
				BufferedReader reader = new BufferedReader(new InputStreamReader(scriptStream))) {
			
			List<String> scriptList = new ArrayList<>();
			String script;
			while ((script = reader.readLine()) != null) {
				scriptList.add(script);
			}
			createNativeQuery(scriptList.toArray(new String[0]));
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			throw new AppException(e.getMessage());
		}
	}
	
	private static void createNativeQuery(String[] sql) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		for (int i = 0; i < sql.length; i++) {
			entityManager.createNativeQuery(sql[i]).executeUpdate();
		}
		transaction.commit();
		entityManager.close();
	}
}
