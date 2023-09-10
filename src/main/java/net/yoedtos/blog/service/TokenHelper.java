package net.yoedtos.blog.service;

import java.util.UUID;

public class TokenHelper {

	public String generate() {
		return UUID.randomUUID().toString();
	}
}
