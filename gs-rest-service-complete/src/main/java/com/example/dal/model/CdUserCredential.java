package com.example.dal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_credential")
public class CdUserCredential {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
	private CdUser username;
	
	@Column (name="password")
	private String password;

	@Column (name = "generated_on")
	private Date generatedOn;
	

	@Column (name = "expires_at")
	private Date expiresAt;

	
	public CdUserCredential() {
		super();
	}

	public CdUserCredential(CdUser user, String password, Date generatedOn, Date expiresAt) {
		super();
		this.username = user;
		this.username.setUserCredential(this);
		this.password = password;
		this.generatedOn = generatedOn;
		this.expiresAt = expiresAt;
	}

	public Long getId() {
		return id;
	}

	public CdUser getUser() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getGeneratedOn() {
		return generatedOn;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	@Override
	public String toString() {
		return "CdUserCredential [id=" + id + ", user=" + username + ", password=" + password + ", generatedOn="
				+ generatedOn + ", expiresAt=" + expiresAt + "]";
	}
}
