package com.cognixia.jump.model;

import java.io.Serializable;

// holds JWT for when we want to return it back
public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String jwt;
	
	public AuthenticationResponse() {
	
	}
	
	public AuthenticationResponse(String jwt)  {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + "]";
	}

}
