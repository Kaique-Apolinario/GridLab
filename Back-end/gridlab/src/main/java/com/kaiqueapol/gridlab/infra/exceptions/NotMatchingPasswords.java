package com.kaiqueapol.gridlab.infra.exceptions;

public class NotMatchingPasswords extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotMatchingPasswords() {
		super("Password and confirmation password aren't the same. Please, try again!");
	}

	public NotMatchingPasswords(String message) {
		super(message);
	}
}