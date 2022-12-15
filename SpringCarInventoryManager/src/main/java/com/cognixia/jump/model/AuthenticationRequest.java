package com.cognixia.jump.model;

import java.io.Serializable;

// model object used to send in username and password for when user
// first authenticates and needs to creat a JWT
public class AuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	public AuthenticationRequest() {
		
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

	@Override
	public String toString() {
		return "AuthenticationRequest [username=" + username + ", password=" + password + "]";
	}

}
