package net.yoedtos.blog.repository.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FSHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger(FSHelper.class);

	private String rootPath;
	
	public FSHelper(String rootPath) {
		this.rootPath = rootPath;
	}

	public void createPath(String path) throws IOException {
		File directory = new File(rootPath + path);
		try {
			if (!directory.exists()) {
				if (!directory.mkdirs()) {
					throw new IOException("Path creation failed");
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}

	public boolean checkFile(String urn) {
		File file = new File(rootPath + urn);
		return file.exists();
	}
	
	public byte[] read(String urn) throws IOException {
		File file = new File(rootPath + urn);
		byte[] binary;
		try (FileInputStream fileInput = new FileInputStream(file)) {
			binary = IOUtils.toByteArray(fileInput);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return binary;
	}
	
	public void write(String urn, byte[] binary) throws IOException {
		File file = new File(rootPath + urn);
		try (FileOutputStream fileOutput = new FileOutputStream(file)) {
			if (!file.exists()) {
				if (!file.createNewFile()) {
					throw new IOException("File creation failed");
				}
			}
			fileOutput.write(binary);
			fileOutput.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
	
	public void delete(String urn) throws IOException {
		File file = new File(rootPath + urn);
		try {
			if (file.exists()) {
				if (!file.delete()) {
					throw new IOException("File delete failed");
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}
}
