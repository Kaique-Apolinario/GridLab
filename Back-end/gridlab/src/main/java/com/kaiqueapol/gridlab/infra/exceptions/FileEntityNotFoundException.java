package com.kaiqueapol.gridlab.infra.exceptions;

public class FileEntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileEntityNotFoundException() {
		super("We couldn't find the file you are trying to download. Please, try again!");
	}

	public FileEntityNotFoundException(String message) {
		super(message);
	}
}