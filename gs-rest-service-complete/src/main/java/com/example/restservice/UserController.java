package com.example.restservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.services.IAuthenticationService;
import com.example.services.IUserService;
import com.example.validation.RestPreconditions;
import com.example.validation.UserValidator;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserValidator validator;
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	@GetMapping
	public List<UserResource> getUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
		// TODO: check if token sent is valid
		return userService.getAll();
	}
	
	@GetMapping(value = "/{username}")
	public List<UserResource> getUserByUsername(@PathVariable("username") String username, 
			@RequestHeader(name=HttpHeaders.AUTHORIZATION, required=false) String accessToken,
			@RequestParam(name="action", required=false) String action,
			@RequestParam(name="name", required=false) String name,
			@RequestParam(name="emailId", required=false) String emailId) {
		
		// if header is not set, throw error
		if (accessToken == null || accessToken.isEmpty()) {
			throw new BadRequestException("Header missing: {" + HttpHeaders.AUTHORIZATION + "}.");
		}
		
		// check if valid access-token exists
		TokenResource tr = authenticationService.getTokenByUsernameAndToken(username, accessToken);
		RestPreconditions.checkTokenValid(tr, username);
	
		List<UserResource> foundUsers = new ArrayList<>();
		
		if (action == null) {
			// return details of the current logged in user
			foundUsers.add(userService.getByUsername(username));
			
		} else if ("search".equalsIgnoreCase(action)) {
			//TODO: Query Builder ?
			
			// create a map of params and populate it with all query params
			Map<String, String> searchParams = new HashMap<String, String>();
			searchParams.put("name", name);
			searchParams.put("emailId", emailId);
			
			foundUsers = userService.getBySearchParams(searchParams);
			
		} else {
			throw new BadRequestException("Invalid action = {" + action + "}. Use 'search' for user lookup.");
		}
		
		return UserValidator.obfuscatePasswordDetails(foundUsers);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserResource createUser(@RequestBody UserResource user) throws BadRequestException {
		validator.checkMinSignupParams(user);
		
		UserResource existingUser = userService.getByUsername(user.getUsername());
		
		if (existingUser != null)
			throw new BadRequestException("Username {" + user.getUsername() + "} already exists!");
		
		UserResource createdUser = userService.create(user);
		return UserValidator.obfuscatePasswordDetails(createdUser);
	}
	
	@PutMapping(value = "/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "username" ) String username, 
    		@RequestBody UserResource resource, @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) throws ResourceNotFoundException {
		// check if valid access-token exists
		TokenResource tr = authenticationService.getTokenByUsernameAndToken(username, accessToken);
		RestPreconditions.checkTokenValid(tr, username);
		
		// get relevant user
		UserResource foundUser = userService.getByUsername(username);
		foundUser = RestPreconditions.checkFound(foundUser);
		
        userService.update(foundUser);
    }

    @DeleteMapping(value = "/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("username") String username, @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) throws ResourceNotFoundException {
    	// check if valid access-token exists
		TokenResource tr = authenticationService.getTokenByUsernameAndToken(username, accessToken);
		RestPreconditions.checkTokenValid(tr, username);
		
		// get relevant user
		UserResource foundUser = userService.getByUsername(username);
		foundUser = RestPreconditions.checkFound(foundUser);
		
		// delete user
    	userService.delete(foundUser);
    }
}
