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
@Table (name="access_token")
public class AccessToken {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
	private CdUser username;
	
	@Column(name="token")
	private String token;
	
	@Column (name = "generated_at")
	private Date generatedAt;
	
	@Column (name = "expires_at")
	private Date expiresAt;

	public AccessToken() {
		super();
	}

	public AccessToken(CdUser username, String token, Date generatedAt, Date expiresAt) {
		super();
		this.username = username;
		this.token = token;
		this.generatedAt = generatedAt;
		this.expiresAt = expiresAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CdUser getUsername() {
		return username;
	}

	public void setUsername(CdUser username) {
		this.username = username;
	}

	public Date getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(Date generatedAt) {
		this.generatedAt = generatedAt;
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AccessToken [id=" + id + ", username=" + username + ", token=" + token + ", generatedAt=" + generatedAt
				+ ", expiresAt=" + expiresAt + "]";
	}
}
