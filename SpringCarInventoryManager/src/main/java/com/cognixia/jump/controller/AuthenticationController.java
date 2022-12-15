package com.cognixia.jump.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.util.JwtUtil;

@RestController
public class AuthenticationController {
	
	// Authentication manager -> validats/authenticates user credentials
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService; // Spring will look for an implementation of userDetailsService and find ours
	
	@Autowired
	JwtUtil jwtUtil;
	
	// create token at http://localhost:8080/authenticate
	// send the username & password to try and generate a token as a response,
	// username and password must already exist in DB
	@PostMapping("/authenticate")
	public ResponseEntity<?> createJwtToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		// try to catch the exception for bad credentials, just so we can set our own
		// message when this doesn't work
		try {
			// first make sure we have valid user checking username and password
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		
		// if no exception is thrown, user is valid
		
		// load in user details
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		// generate token for user
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		// return token
		return ResponseEntity.status(201).body( new AuthenticationResponse(jwt));
		
	}
	

}
