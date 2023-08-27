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
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.util.Constants;

public class DataCreator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataCreator.class);
	
	public DataCreator() {}
	
	public DataCreator createAdminUser() {
		createNativeQuery(new String[] {Constants.SCRIPT_ADMIN});
		return this;
	}
	
	public void createDemoData() {
		try (InputStream scriptStream = new FileInputStream(new File(Constants.DEMO_DATA));
				BufferedReader reader = new BufferedReader(new InputStreamReader(scriptStream))) {
			
			List<String> scriptList = new ArrayList<>();
			String script;
			while ((script = reader.readLine()) != null) {
				scriptList.add(script);
			}
			createNativeQuery(scriptList.toArray(new String[0]));
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
	}
	
	private void createNativeQuery(String[] sql) {
		EntityManager entityManager = DaoHandler.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		for (int i = 0; i < sql.length; i++) {
			entityManager.createNativeQuery(sql[i]).executeUpdate();
		}
		
		transaction.commit();
		entityManager.close();
	}
}
