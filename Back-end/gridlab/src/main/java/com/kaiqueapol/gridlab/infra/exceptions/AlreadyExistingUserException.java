package com.kaiqueapol.gridlab.infra.exceptions;

public class AlreadyExistingUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AlreadyExistingUserException() {
		super("Username already in use. Please, try again!");
	}

	public AlreadyExistingUserException(String message) {
		super(message);
	}
}