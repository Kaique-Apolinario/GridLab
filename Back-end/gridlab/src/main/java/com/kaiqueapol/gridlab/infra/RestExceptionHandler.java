package com.kaiqueapol.gridlab.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kaiqueapol.gridlab.infra.exceptions.AlreadyExistingUserException;
import com.kaiqueapol.gridlab.infra.exceptions.FileEntityNotFoundException;
import com.kaiqueapol.gridlab.infra.exceptions.InvalidFileException;
import com.kaiqueapol.gridlab.infra.exceptions.InvalidTokenException;
import com.kaiqueapol.gridlab.infra.exceptions.NosyException;
import com.kaiqueapol.gridlab.infra.exceptions.NotMatchingPasswords;
import com.kaiqueapol.gridlab.infra.exceptions.TooManySheetsException;
import com.kaiqueapol.gridlab.infra.exceptions.UserNotFoundException;

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
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(AlreadyExistingUserException.class)
	public ResponseEntity<String> alreadyExistingUserException(AlreadyExistingUserException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> userNotFoundException(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() + ". Try again!");
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundException(UserNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

	@ExceptionHandler(NotMatchingPasswords.class)
	public ResponseEntity<String> notMatchingPasswords(NotMatchingPasswords e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(NosyException.class)
	public ResponseEntity<String> nosyException(NosyException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<String> invalidTokenException(InvalidTokenException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}

}
