package com.kaiqueapol.gridlab.infra.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("Invalid credentials. Please, try again!");
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}