package com.example.services;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.example.dal.model.AccessToken;
import com.example.restservice.LoginResponse;
import com.example.restservice.TokenResource;
import com.example.restservice.UserResource;

@Component
public interface IAuthenticationService {
	LoginResponse authenticate(String username, String password) throws NoSuchAlgorithmException;
	String generateToken(UserResource user); //TODO: needed?
	TokenResource getTokenByUsernameAndToken(String username, String accessToken);
	void logoutByUsernameAndToken(String username, String token);
	TokenResource getTokenResourceFromAccessToken(AccessToken accessToken); //TODO: move to adapater class?
	AccessToken getAccessTokenFromTokenResource(TokenResource tokenResource); //TODO: move to adapater class?
	
}
