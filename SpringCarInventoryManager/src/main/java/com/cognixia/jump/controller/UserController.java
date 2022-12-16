package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
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
	
	@CrossOrigin
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User with id = " + id + " not found.");
		}
		
		return ResponseEntity.status(200).body(found.get());
	}
	
	@CrossOrigin
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		
		user.setId(null);
		
		// before user gets saved to DB, password should be encoded
		user.setPassword(encoder.encode(user.getPassword()));
		
		// after encoded, save user
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	@CrossOrigin
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User updatedUser) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(updatedUser.getId());
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User with id = " + updatedUser.getId() + " not found and can't be updated.");
		}
		else {
			updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
			return ResponseEntity.status(202).body(repo.save(updatedUser));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User with id = " + id + " not found. Couldn't be deleted.");
		}
		else {
			repo.deleteById(id);
			return ResponseEntity.status(202).body("Car deleted");
		}
	}

}

























