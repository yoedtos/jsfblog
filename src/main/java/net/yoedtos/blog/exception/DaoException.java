package net.yoedtos.blog.exception;

@SuppressWarnings("serial")
public class DaoException extends Exception {

	public DaoException() {}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
