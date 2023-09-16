package net.yoedtos.blog.exception;

@SuppressWarnings("serial")
public class MailerException extends Exception {

	public MailerException() {}

	public MailerException(String message) {
		super(message);
	}
	
	public MailerException(String message, Throwable cause) {
		super(message, cause);
	}	
}
