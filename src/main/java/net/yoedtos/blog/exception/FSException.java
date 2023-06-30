package net.yoedtos.blog.exception;

@SuppressWarnings("serial")
public class FSException extends Exception {

	public FSException() {}

	public FSException(String message, Throwable cause) {
		super(message, cause);
	}

	public FSException(String message) {
		super(message);
	}
}
