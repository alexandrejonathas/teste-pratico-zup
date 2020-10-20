package br.com.zup.bancodigital.infrastructure.email;

public class MailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MailException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailException(String message) {
		super(message);
	}

}
