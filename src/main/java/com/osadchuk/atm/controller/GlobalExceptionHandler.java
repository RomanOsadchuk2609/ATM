package com.osadchuk.atm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(500).body(ex.getMessage());
	}
}
