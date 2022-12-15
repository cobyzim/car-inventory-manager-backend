package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;


@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	// load in encoder for the project (located in security config)
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("User with id = " + id + " not found");
		}
		
		return ResponseEntity.status(200).body(found.get());
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		
		user.setId(null);
		
		// before user gets saved to DB, password should be encoded
		user.setPassword(encoder.encode(user.getPassword()));
		
		// after encoded, save user
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
		
	}

}
