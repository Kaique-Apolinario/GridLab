package com.kaiqueapol.sheetmanager.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kaiqueapol.sheetmanager.exceptions.FileEntityNotFoundException;
import com.kaiqueapol.sheetmanager.exceptions.InvalidFileException;
import com.kaiqueapol.sheetmanager.exceptions.TooManySheetsException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidFileException.class)
	public ResponseEntity<String> invalidFileException(InvalidFileException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(FileEntityNotFoundException.class)
	public ResponseEntity<String> fileEntityNotFoundException(FileEntityNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(TooManySheetsException.class)
	public ResponseEntity<String> tooManySheetsException(TooManySheetsException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

}
