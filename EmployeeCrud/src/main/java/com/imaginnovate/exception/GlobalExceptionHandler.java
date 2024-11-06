package com.imaginnovate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InValidEmailException.class)
	public ResponseEntity<String> handleInvalidEmailException(InValidEmailException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<String> handleInvalidEmailException(EmailAlreadyExistsException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	
	
	

}
