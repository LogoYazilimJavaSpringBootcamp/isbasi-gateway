package com.isbasi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExcepitonHandler {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handle(ExpiredJwtException exception) {
		return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);

	}

}
