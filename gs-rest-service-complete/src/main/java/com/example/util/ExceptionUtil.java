package com.example.util;

import com.example.restservice.TokenResourceStatus;

public class ExceptionUtil {
	private static String INVALID_TOKEN_TEMPLATE = "Invalid access-token for user [username, token]: [%s, %s]. ";
	private static String EXPIRED_TOKEN_TEMPLATE = "Invalid access-token for user [username, token]: [%s, %s]. ";
	
	public static String getMessageByTokenStatus(String username, String token, TokenResourceStatus tokenStatus) {
		String message = "";
		
		switch (tokenStatus) {
			case EXPIRED: message = String.format(EXPIRED_TOKEN_TEMPLATE, username, token);
			case INVALID: message = String.format(INVALID_TOKEN_TEMPLATE, username, token);
			default: message = String.format(INVALID_TOKEN_TEMPLATE, username, token);
		}
		
		return message;
	}
}
