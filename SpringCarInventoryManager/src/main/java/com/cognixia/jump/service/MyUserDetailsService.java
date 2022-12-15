package com.cognixia.jump.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

// Service class that manages/helps locate users so they can be authenticated
// and provide spring security with user info
@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository repo;

	// When spring security needs to check for a user, will call loadUserByUsername
	// from this class
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userFound = repo.findByUsername(username);
		
		if (userFound.isEmpty()) {
			throw new UsernameNotFoundException("Username " + username + "not found");
		}
		
		return new MyUserDetails(userFound.get());
	}
}
