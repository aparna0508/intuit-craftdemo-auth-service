package com.example.services;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.dal.model.AccessToken;
import com.example.dal.model.CdUser;
import com.example.dal.model.CdUserCredential;
import com.example.dal.repository.IAccessTokenRepository;
import com.example.dal.repository.ICdUserCredentialRepository;
import com.example.exceptions.MyAccessDeniedException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.restservice.LoginResponse;
import com.example.restservice.TokenResource;
import com.example.restservice.TokenResourceStatus;
import com.example.restservice.UserResource;
import com.example.util.DateTimeUtil;
import com.example.util.ExceptionUtil;

@Component
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	private static final Integer AUTH_EXPIRE_TIME_IN_MIN = 3;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICdUserCredentialRepository credentialsRepository;
	
	@Autowired
	private IAccessTokenRepository accessTokenRepository;
	
	@Override
	public LoginResponse authenticate(String username, String password) throws NoSuchAlgorithmException {
		LoginResponse loginResponse = null;
		
		// find suitable user
		UserResource ur = userService.getByUsername(username);
		if (ur == null) {
			throw new ResourceNotFoundException("{" + username + "} not found.");
		}
		
		// convert user resource to relevant DB model
		CdUser user = userService.getCdUserFromUserResource(ur);
			
		// hash the sent password and match it with db
		String hashedPasswd = DateTimeUtil.getHashed(password, user.getSalt());
		CdUserCredential credentials = credentialsRepository.findByUsernameAndPassword(user, hashedPasswd);
		
		if (credentials == null) {
			throw new MyAccessDeniedException("Incorrect password: {" + password + "} for user: {" + username + "}");
		}
		
		// check if a valid token exists already
		AccessToken storedToken = accessTokenRepository.findByUsername(user);
		AccessToken newToken = null;
		
		// If the token does not exist or is expired, generate a new token
		if (storedToken == null || DateTimeUtil.expired(storedToken.getExpiresAt())) {
			// generate a new token
			String accessToken = generateToken(ur);
			Date generatedAt = DateTimeUtil.getCurrentTime();
			Date expiresAt = DateTimeUtil.getExpiryTime(AUTH_EXPIRE_TIME_IN_MIN);
			newToken = new AccessToken(user, accessToken, generatedAt, expiresAt);
		}
		
		// Assign the newly generated token if no such token exists
		if (storedToken == null) {
			storedToken = newToken;
		} else {
			// just need to update the existing stored token if expired
			if (DateTimeUtil.expired(storedToken.getExpiresAt())) {
				storedToken.setToken(newToken.getToken());
				storedToken.setGeneratedAt(newToken.getGeneratedAt());
				storedToken.setExpiresAt(newToken.getExpiresAt());
			} else {
				// update the expiry time each time user logs in
				storedToken.setExpiresAt(DateTimeUtil.getExpiryTime(AUTH_EXPIRE_TIME_IN_MIN));
			}
		}
		
		// save it in repo
		accessTokenRepository.save(storedToken);
		
		// prepare login response
		loginResponse = new LoginResponse(credentials.getUser().getUsername(), storedToken.getToken(), 
				storedToken.getGeneratedAt(), storedToken.getExpiresAt());
			 
		return loginResponse;
	}

	@Override
	public String generateToken(UserResource user) {
		// TODO token logic
		return DateTimeUtil.getRandomSalt();
	}

	@Override
	public TokenResource getTokenByUsernameAndToken(String username, String sentToken) {
		TokenResource tr = null;
		
		// get the user by username
		UserResource ur = userService.getByUsername(username);
		if (ur==null) {
			throw new ResourceNotFoundException(username + " not found");
		}

		// get corresponding access-token
		CdUser user = userService.getCdUserFromUserResource(ur);
		AccessToken storedTokenObj = accessTokenRepository.findByUsername(user);
		
		// check if stored token matches the sent token
		if (storedTokenObj == null || !storedTokenObj.getToken().equals(sentToken)) {
			throw new MyAccessDeniedException(ExceptionUtil.getMessageByTokenStatus(username, sentToken, TokenResourceStatus.INVALID));
		}
		
		// set the token status based on expiration time
		tr = getTokenResourceFromAccessToken(storedTokenObj);
		tr.setExpired(DateTimeUtil.expired(tr.getExpiresAt()));
		
		return tr;
	}

	
	@Override
	@Transactional
	public void logoutByUsernameAndToken(String username, String sentToken) {
		// get the user by username
		UserResource ur = userService.getByUsername(username);
		if (ur==null) {
			throw new ResourceNotFoundException(username + " not found");
		}
		
		// get corresponding access-token
		CdUser user = userService.getCdUserFromUserResource(ur);
		AccessToken storedTokenObj = accessTokenRepository.findByUsernameAndToken(user, sentToken);
		
		// check if stored token matches the sent token
		if (storedTokenObj == null) {
			throw new MyAccessDeniedException(ExceptionUtil.getMessageByTokenStatus(username, sentToken, TokenResourceStatus.INVALID));
		}
		
		// delete the access-token
		System.out.println("======================" + storedTokenObj + "===================");
		long num = accessTokenRepository.deleteByUsername(storedTokenObj.getUsername());
		System.out.println("======================" + num);
//		accessTokenRepository.flush();
	}

	@Override
	public TokenResource getTokenResourceFromAccessToken(AccessToken accessToken) {
		TokenResource tr = null;
		boolean defaultExpireStatus = false;
		
		if (accessToken != null) {
			tr = new TokenResource(accessToken.getId(), 
								  accessToken.getToken(), 
								  accessToken.getUsername().getUsername(), 
								  accessToken.getGeneratedAt(), 
								  accessToken.getExpiresAt(), 
								  defaultExpireStatus); // setting expired status as false by default
		}
		
		return tr;
	}

	@Override
	public AccessToken getAccessTokenFromTokenResource(TokenResource tokenResource) {
		AccessToken at = null;
		
		if (tokenResource != null) {
			// get user from the token resource
			UserResource ur = userService.getByUsername(tokenResource.getUsername());
			
			if (ur != null) {
				// get relevant user object of DB
				CdUser user = userService.getCdUserFromUserResource(ur);
				at = new AccessToken(
						user,
						tokenResource.getToken(), 
						tokenResource.getGeneratedOn(), 
						tokenResource.getExpiresAt()); 
			}
		}
		
		return at;
	}
	
}