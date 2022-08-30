package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)  {
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse,  HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)  
	public final ResponseEntity<Object> handleNotFoundExceptions(ResourceNotFoundException ex, WebRequest request)  
	{  
		ExceptionResponse exceptionResponse= new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));  
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);  
	} 
	
	@ExceptionHandler(BadRequestException.class)  
	public final ResponseEntity<Object> handleBadRequestExceptions(BadRequestException ex, WebRequest request)  
	{  
		ExceptionResponse exceptionResponse= new ExceptionResponse(HttpStatus.BAD_REQUEST.value() ,ex.getMessage(), request.getDescription(false));  
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);  
	}
	
	@ExceptionHandler(MyAccessDeniedException.class)  
	public final ResponseEntity<Object> handleAccessDeniedExceptions(MyAccessDeniedException ex, WebRequest request)  
	{  
		ExceptionResponse exceptionResponse= new ExceptionResponse(HttpStatus.FORBIDDEN.value(),ex.getMessage(), request.getDescription(false));  
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.FORBIDDEN);  
	}

}
