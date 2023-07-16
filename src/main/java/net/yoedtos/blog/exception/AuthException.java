package net.yoedtos.blog.exception;

@SuppressWarnings("serial")
public class AuthException extends Exception {

	public AuthException() {
		super();
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}
}
