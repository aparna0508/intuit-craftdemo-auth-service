package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MyInternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyInternalServerException (String message) {
		super(message);
	}
}