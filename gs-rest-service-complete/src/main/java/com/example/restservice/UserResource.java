package com.example.restservice;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserResource {
	private String username;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String password; // password won't be serialized if it is null
	private String name;
	private String emailId;
	private String phoneNo;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String salt;
	private String secretQ;
	
	//TODO: need default constructor for autowired; but can't allow user creation w/o uname and password
	public UserResource() {
		super();
	}
	
	public UserResource(String username, String password, String name, String emailId, String phoneNo, String salt,
			String secretQ) {
		this(username, password);
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.salt = salt;
		this.secretQ = secretQ;
	}
	
	public UserResource(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getSecretQ() {
		return secretQ;
	}

	public void setSecretQ(String secretQ) {
		this.secretQ = secretQ;
	}

	@Override
	public String toString() {
		return "UserResource [username=" + username + ", password=" + password + ", name=" + name + ", emailId="
				+ emailId + ", phoneNo=" + phoneNo + ", salt=" + salt + ", secretQ=" + secretQ + "]";
	}
}
