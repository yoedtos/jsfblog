package net.yoedtos.blog.repository.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.util.Constants;

public class PropertiesHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHelper.class);

	private File propsFile;

	public PropertiesHelper(String propsFilename) {
		super();
		this.propsFile = new File(propsFilename);
	}

	public Properties read() throws IOException {
		Properties properties = new Properties();

		try (FileInputStream input = new FileInputStream(propsFile)) {
			properties.load(input);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return properties;
	}

	public void write(Properties properties) throws IOException {
		try (FileOutputStream output = new FileOutputStream(propsFile)) {
			properties.store(output, Constants.PROPERTY_FILE_HEADER);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
}
