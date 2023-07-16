package net.yoedtos.blog.service;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class CryptoHelper {

	private StrongPasswordEncryptor encryptor;
	
	public CryptoHelper() {
		encryptor = new StrongPasswordEncryptor();
	}

	public String encrypt(String password) {
		return encryptor.encryptPassword(password);
	}

	public boolean isValidPassword(String password, String encoded) {
		return encryptor.checkPassword(password, encoded);
	}
}
