package com.kaiqueapol.sheetmanager.exceptions;

public class TooManySheetsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TooManySheetsException() {
		super("It would be mroe sheets than current rows! Please, try a smaller number.");
	}

	public TooManySheetsException(String message) {
		super(message);
	}
}
