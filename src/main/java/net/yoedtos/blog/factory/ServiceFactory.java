package net.yoedtos.blog.factory;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.AppException;
import net.yoedtos.blog.service.Service;

public final class ServiceFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);
	
	private ServiceFactory() {}

	@SuppressWarnings({ "unchecked" })
	public static <T> T create(Class<? extends Service> clazz) {
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
