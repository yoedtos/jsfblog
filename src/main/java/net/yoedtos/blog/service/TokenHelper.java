package net.yoedtos.blog.service;

import java.util.UUID;

public class TokenHelper {
	
	public String generate() {
		return UUID.randomUUID().toString();
	}
	
	public void validate(String token) {
		try {
			UUID.fromString(token);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}
}
