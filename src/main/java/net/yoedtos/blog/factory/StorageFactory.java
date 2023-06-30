package net.yoedtos.blog.factory;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.repository.Storage;

public final class StorageFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageFactory.class);

	private StorageFactory() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T create(Class<? extends Storage> clazz) {
		T t = null;
		try {
			t = (T) clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException 
				| IllegalArgumentException | InvocationTargetException 
				| NoSuchMethodException | SecurityException e) {
			LOGGER.error(e.getMessage());
			throw new AppException("Failed to create: " + clazz.getName());
		}
		return t;
	}
}
