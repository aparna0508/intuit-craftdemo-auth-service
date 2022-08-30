package com.example.validation;

import java.util.List;

import com.example.exceptions.MyAccessDeniedException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.restservice.TokenResource;
import com.example.restservice.TokenResourceStatus;
import com.example.util.ExceptionUtil;

public class RestPreconditions {
	
    public static <T> T checkFound(T resource) throws ResourceNotFoundException {
        if (resource == null) {
            throw new ResourceNotFoundException("Not found");
        }
        return resource;
    }
    
    public static <T> List<T> checkFound(List<T> resource) throws ResourceNotFoundException {
        if (resource == null) {
            throw new ResourceNotFoundException("Not found");
        }
        return resource;
    }

	public static void checkTokenValid(TokenResource tr, String username) {
		if (tr == null || tr.isExpired())
			throw new MyAccessDeniedException(ExceptionUtil.getMessageByTokenStatus(username, tr.getToken(), TokenResourceStatus.EXPIRED));
	}
    
}
