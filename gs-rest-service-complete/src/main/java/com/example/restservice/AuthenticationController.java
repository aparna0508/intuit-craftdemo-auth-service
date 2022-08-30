package com.example.restservice;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.services.IAuthenticationService;
import com.example.validation.RestPreconditions;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private IAuthenticationService authService;
	
	@PostMapping(value = "/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) throws ResourceNotFoundException, NoSuchAlgorithmException {
		return RestPreconditions.checkFound(authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()));
	}
	
	@PostMapping(value = "/{username}/logout")
	public void logout(
			@PathVariable("username") String username, 
			@RequestHeader(name=HttpHeaders.AUTHORIZATION, required=false) String accessToken) {
		
		// if header is not set, throw error
		if (accessToken == null || accessToken.isEmpty()) {
			throw new BadRequestException("Header missing: {" + HttpHeaders.AUTHORIZATION + "}.");
		}
		
		authService.logoutByUsernameAndToken(username, accessToken);
	}

	@GetMapping(value = "/{username}")
	public TokenResource getTokenStatusForUser(@PathVariable("username") String username, 
			@RequestHeader(name=HttpHeaders.AUTHORIZATION, required=false) String accessToken) {
		// if header is not set, throw error
		if (accessToken == null || accessToken.isEmpty()) {
			throw new BadRequestException("Header missing: {" + HttpHeaders.AUTHORIZATION + "}.");
		}
		
		return RestPreconditions.checkFound(authService.getTokenByUsernameAndToken(username, accessToken));
	}
}
