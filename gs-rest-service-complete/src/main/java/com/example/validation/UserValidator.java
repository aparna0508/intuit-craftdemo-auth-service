package com.example.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.exceptions.BadRequestException;
import com.example.restservice.UserResource;

@Component
public class UserValidator {
	
	public boolean checkMinSignupParams(UserResource user) throws BadRequestException {
		if (user == null) {
			throw new BadRequestException("Empty user. Need minimum {username: '', password: '', name: ''}");
		}
		
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			throw new BadRequestException("Empty {username: ''}. Provide suitable value.");
		}
		
		if (user.getPassword() == null || user.getPassword().isEmpty()) {
			throw new BadRequestException("Empty {password: ''}. Provide suitable value.");
		}
		
		if (user.getName() == null || user.getName().isEmpty()) {
			throw new BadRequestException("Empty {name: ''}. Provide suitable value.");
		}
		
		return true;
	}
	
	/* Deliberately sets password and salt information to null.
	 * To be called while returning response object 
	 * */
	public static UserResource obfuscatePasswordDetails(UserResource user) {
		if (user != null) {
			user.setPassword(null);
			user.setSalt(null);
		}
		
		return user;
	}
	
	public static List<UserResource> obfuscatePasswordDetails(List<UserResource> users) {
		if (users != null) {
			for (UserResource user: users)
				obfuscatePasswordDetails(user);
		}
		
		return users;
	}


}
