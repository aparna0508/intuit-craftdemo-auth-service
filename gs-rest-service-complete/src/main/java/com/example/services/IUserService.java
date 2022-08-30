package com.example.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.dal.model.CdUser;
import com.example.restservice.UserResource;

@Component
public interface IUserService {
	UserResource create(UserResource user); //TODO: Builder pattern?
	List<UserResource> getAll();
	UserResource getByUsername(String username);
	void update(UserResource user);
	void delete(UserResource user);
	UserResource getUserResourceFromCdUser(CdUser cduser);
	CdUser getCdUserFromUserResource(UserResource user);
	List<UserResource> getBySearchParams(Map<String, String> searchParams);
}
