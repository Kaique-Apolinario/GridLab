package com.kaiqueapol.sheetmanager.exceptions;

public class InvalidFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidFileException() {
		super("Uploaded media is not a .xlsx or .xls file.");
	}

	public InvalidFileException(String message) {
		super(message);
	}
}
