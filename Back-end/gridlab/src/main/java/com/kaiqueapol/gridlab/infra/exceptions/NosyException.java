package com.kaiqueapol.gridlab.infra.exceptions;

public class NosyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NosyException() {
		super("Trying to access another user's file library.");
	}

	public NosyException(String message) {
		super(message);
	}
}