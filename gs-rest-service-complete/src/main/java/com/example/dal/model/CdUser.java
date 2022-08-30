package com.example.dal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cdusers")
public class CdUser {
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name = "phoneNo")
	private String phoneNo;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name = "secretQ")
	private String secretQ;
	
	@OneToOne(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CdUserCredential userCredential;
	
	@OneToOne(mappedBy = "username", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccessToken accessToken;
	
	public CdUser() {
		super();
	}

	public CdUser(String username, String name, String emailId, String phoneNo, String salt, String secretQ) {
		super();
		this.username = username;
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.salt = salt;
		this.secretQ = secretQ;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public CdUserCredential getUserCredential() {
		return userCredential;
	}

	public void setUserCredential(CdUserCredential userCredential) {
		this.userCredential = userCredential;
	}
	
	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecretQ() {
		return secretQ;
	}

	public void setSecretQ(String secretQ) {
		this.secretQ = secretQ;
	}

	@Override
	public String toString() {
		return "CdUser [username=" + username + ", name=" + name + ", emailId=" + emailId + ", phoneNo=" + phoneNo
				+ ", salt=" + salt + ", secretQ=" + secretQ + ", userCredential=" + userCredential + ", accessToken="
				+ accessToken + "]";
	}
}
