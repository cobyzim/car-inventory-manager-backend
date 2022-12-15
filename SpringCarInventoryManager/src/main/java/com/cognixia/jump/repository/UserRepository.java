package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends  JpaRepository<User, Integer> {

	
	// called by MyUserDetailsService whenever username + password
	// are passed through API request so we have a way to look up
	// the user
	public Optional<User> findByUsername(String username);
	
	
	
}
