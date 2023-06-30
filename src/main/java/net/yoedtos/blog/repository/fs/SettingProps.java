package net.yoedtos.blog.repository.fs;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.repository.Storage;
import net.yoedtos.blog.util.Constants;

public class SettingProps implements Storage<Properties> {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingProps.class);
	
	private PropertiesHelper propertiesHelper;
	
	public SettingProps() {
		super();
		this.propertiesHelper = new PropertiesHelper(Constants.PROPERTY_FILE_NAME);
	}

	@Override
	public void store(Properties settings) throws FSException {
		try {
			propertiesHelper.write(settings);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new FSException(e.getMessage());
		}
	}

	@Override
	public void drop(Properties t) throws FSException {
		throw new FSException("Not implemented");
	}

	@Override
	public Properties load(Object obj) throws FSException {
		Properties properties;
		try {
			properties = propertiesHelper.read();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new FSException(e.getMessage());
		}
		return properties;
	}

	@Override
	public Properties replace(Properties t) throws FSException {
		return null;
	}

	@Override
	public Object find(Object obj) throws FSException {
		return null;
	}
}
