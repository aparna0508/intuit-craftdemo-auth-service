package com.example.restservice;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {
	private String username;
	private String token;
	private Date createdOn;
	private Date expireAt;
	
	public LoginResponse() {
		super();
	}
	
	public LoginResponse(String username, String token, Date createdOn, Date expireAt) {
		super();
		this.username = username;
		this.token = token;
		this.createdOn = createdOn;
		this.expireAt = expireAt;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getExpireAt() {
		return expireAt;
	}
	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

	@Override
	public String toString() {
		return "AuthResponse [username=" + username + ", token=" + token + ", createdOn=" + createdOn + ", expireAt="
				+ expireAt + "]";
	}
}
