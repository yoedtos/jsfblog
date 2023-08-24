package net.yoedtos.blog.exception;

@SuppressWarnings("serial")
public class SearchException extends Exception {

	public SearchException() {}
	
	public SearchException(String message) {
		super(message);
	}
	
	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
}
