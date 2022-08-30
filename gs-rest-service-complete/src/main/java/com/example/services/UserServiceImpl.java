package com.example.services;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dal.model.CdUser;
import com.example.dal.model.CdUserCredential;
import com.example.dal.repository.ICdUserCredentialRepository;
import com.example.dal.repository.ICdUserRepository;
import com.example.exceptions.MyInternalServerException;
import com.example.restservice.UserResource;
import com.example.util.DateTimeUtil;

@Component
public class UserServiceImpl implements IUserService {
	private static final Integer PASSWORD_EXPIRE_TIME_IN_MIN = 24 * 60 * 365;
	
	//private static long COUNTER = 0;
	//private List<UserResource> users;
	
	@Autowired
	private ICdUserRepository userRepository;
	
	@Autowired
	private ICdUserCredentialRepository credentialsRepository;
	
	public UserServiceImpl() {
		// TODO inject di
		//users = new ArrayList<>();
	}

	@Override
	public UserResource create(UserResource user) {
		// generate a salt and store it for new user
		CdUser cduser = getCdUserFromUserResource(user);
		cduser.setSalt(DateTimeUtil.getRandomSalt());
		
		// persist new user to repository
		CdUser createdUser = userRepository
				.save(cduser);
		
		// Hash the password and store
		String hashedPassword = "";
		try {
			hashedPassword = DateTimeUtil.getHashed(user.getPassword(), cduser.getSalt());
		} catch (NoSuchAlgorithmException e) {
			throw new MyInternalServerException("Failed to hash password. Exception: " + e.getMessage());
		}
		
		credentialsRepository.save(new CdUserCredential(createdUser, hashedPassword, DateTimeUtil.getCurrentTime(), DateTimeUtil.getExpiryTime(PASSWORD_EXPIRE_TIME_IN_MIN)));
		
		return getUserResourceFromCdUser(createdUser);
	}

	@Override
	public UserResource getByUsername(String username) {
		UserResource u = null;
		
		CdUser cduser = userRepository.findByUsername(username);
		
		if (cduser != null) {
			u = getUserResourceFromCdUser(cduser);
		}
		
		return u;
		
	}
	
	@Override
	public List<UserResource> getAll() {
		List<UserResource> userResources = new ArrayList<UserResource>();
		List<CdUser> cdusers = new ArrayList<CdUser>();

		// get from repository
		userRepository.findAll().forEach(cdusers::add);
		
		for (CdUser u : cdusers) {
			userResources.add(getUserResourceFromCdUser(u));
		}
		
		return userResources;
	}
	
	@Override
	public void update(UserResource user) {
		CdUser cduser = userRepository.findByUsername(user.getUsername());
		if (cduser != null) {
			// note: can't update id and username
			cduser.setName(user.getName());
			cduser.setEmailId(user.getEmailId());
			cduser.setPhoneNo(user.getPhoneNo());
			
			userRepository.save(cduser);
		}
	}

	@Override
	public void delete(UserResource user) {
		CdUser cduser = userRepository.findByUsername(user.getUsername());
		if (cduser != null) {
			userRepository.delete(cduser);
		}
	}
	
	@Override
	public UserResource getUserResourceFromCdUser(CdUser cduser) {
		//TODO: put in adapter class
		UserResource u = null;
		
		if (cduser != null) {
			String passwd = cduser.getUserCredential() == null ? "" : cduser.getUserCredential().getPassword();
			
			// set empty password and salt value
			u = new UserResource(
					cduser.getUsername(),
					passwd,
					cduser.getName(),
					cduser.getEmailId(),
					cduser.getPhoneNo(),
					cduser.getSalt(),
					cduser.getSecretQ());
		}
		
		return u;
	}
	
	@Override
	public CdUser getCdUserFromUserResource(UserResource user) {
		//TODO: put in adapter class
		CdUser cduser = null;
		
		if (user != null) {
			cduser = new CdUser(
					user.getUsername(),
					user.getName(),
					user.getEmailId(),
					user.getPhoneNo(),
					user.getSalt(),
					user.getSecretQ());
		}
		
		return cduser;
	}

	@Override
	public List<UserResource> getBySearchParams(Map<String, String> searchParams) {
		if (searchParams == null || searchParams.isEmpty())
			return this.getAll();
		
		// TODO: better way to get from map
		// TODO: Pagination
		String name = searchParams.get("name");
		String emailId = searchParams.get("emailId");
		
		List<CdUser> cdusers = new ArrayList<>();
		
		if (name != null && emailId != null) {
			cdusers = userRepository.findByNameAndEmailId(name, emailId);
		} else if (name != null) {
			cdusers = userRepository.findByName(name);
		} else if (emailId != null) {
			cdusers = userRepository.findByEmailId(emailId);
		} else {
			cdusers = userRepository.findAll();
		}
		
		// convert cduser to user resource
		List<UserResource> users = new ArrayList<>();
		for (CdUser cduser : cdusers) {
			users.add(getUserResourceFromCdUser(cduser));
		}
		
		return users;
	}
	
}
