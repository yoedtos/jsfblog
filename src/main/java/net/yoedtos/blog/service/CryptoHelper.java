package net.yoedtos.blog.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class CryptoHelper {
	private static final int BADGE_SIZE = 50;
	private static final byte[] PIN = new byte[] {35, 40, 85, 74, 108, 109, 115, 50, 53, 108, 79};
	
	private StrongPasswordEncryptor encryptor;
	private BasicTextEncryptor txtEncryptor;
	
	public CryptoHelper() {
		encryptor = new StrongPasswordEncryptor();
		txtEncryptor = new BasicTextEncryptor();
		txtEncryptor.setPassword(new String(PIN));
	}

	public String encrypt(String password) {
		return encryptor.encryptPassword(password);
	}

	public boolean isValidPassword(String password, String encoded) {
		return encryptor.checkPassword(password, encoded);
	}
	
	public String encode(String clear) {
		return txtEncryptor.encrypt(clear);
	}

	public String decode(String coded) {
		return txtEncryptor.decrypt(coded);
	}
	
	public String random() {
		return RandomStringUtils
				.randomAlphanumeric(BADGE_SIZE);
	}
}
