package com.example.exceptions;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ExceptionResponse {
	private int code;
	private String message;
	private String details;
	private Date generatedOn;

	public ExceptionResponse() {
		super();
	}
	
	public ExceptionResponse(int code, String message, String details) {
		super();
		this.code = code;
		this.message = message;
		this.details = details;
		this.generatedOn = Calendar.getInstance().getTime();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
	public Date getGeneratedOn() {
		return generatedOn;
	}
	
}
