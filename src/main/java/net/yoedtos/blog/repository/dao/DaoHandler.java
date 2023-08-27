package net.yoedtos.blog.repository.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.repository.fs.PropertiesHelper;
import net.yoedtos.blog.util.Constants;

public class DaoHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoHandler.class);
	
	private static EntityManagerFactory factory;

	private DaoHandler() {}
	
	public static void init() {
		Map<String, String> configMap = createConfigMap(Constants.APP_PROPS);
		
		if(configMap.isEmpty()) {
			factory = Persistence.createEntityManagerFactory(Constants.PERSIST_UNIT);
		} else {
			factory = Persistence.createEntityManagerFactory(Constants.PERSIST_UNIT, configMap);
		}
	}
	
	public static void shutdown() {
		factory.close();
	}
	
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	private static Map<String, String> createConfigMap(String config) {
		PropertiesHelper propertiesHelper = new PropertiesHelper(config);
		try {
			Properties properties = propertiesHelper.read();
			return Maps.fromProperties(properties);
		} catch (FileNotFoundException e) {
			LOGGER.info(e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
		
		return new HashMap<>();
	}
}
