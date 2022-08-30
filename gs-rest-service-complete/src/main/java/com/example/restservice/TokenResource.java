package com.example.restservice;

import java.util.Date;

public class TokenResource {
	private Long id;
	private String token;
	private String username;
	private Date generatedOn;
	private Date expiresAt;
	private boolean expired;
	
	public TokenResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TokenResource(Long id, String token, String username, Date generatedOn, Date expiresAt, boolean expired) {
		super();
		this.id = id;
		this.token = token;
		this.username = username;
		this.generatedOn = generatedOn;
		this.expiresAt = expiresAt;
		this.expired = expired;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getGeneratedOn() {
		return generatedOn;
	}
	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
	}
	public Date getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	@Override
	public String toString() {
		return "TokenResource [id=" + id + ", token=" + token + ", username=" + username + ", generatedOn="
				+ generatedOn + ", expiresAt=" + expiresAt + ", expired=" + expired + "]";
	}
}
