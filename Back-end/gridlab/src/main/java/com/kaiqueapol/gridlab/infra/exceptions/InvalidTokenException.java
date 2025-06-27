package com.kaiqueapol.gridlab.infra.exceptions;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
		super("Sign in again to confirm your identity.");
	}

	public InvalidTokenException(String message) {
		super(message);
	}
}