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
}
