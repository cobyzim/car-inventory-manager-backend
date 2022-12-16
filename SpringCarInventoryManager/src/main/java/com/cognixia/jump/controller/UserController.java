package com.cognixia.jump.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.UserCar;
import com.cognixia.jump.model.UserCarKey;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserCarService;


@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	UserCarService service;
	
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
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("User with id = " + id + " not found");
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
	public ResponseEntity<?> updateUser(@Valid @RequestBody User updatedUser) {
		
		Optional<User> found = repo.findById(updatedUser.getId());
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Car with id = " + updatedUser + " not found");
		}
		else {
			Collection<UserCar> userCarList = found.get().getUserCar();
			for (UserCar uc: updatedUser.getUserCar()) {
				if (!userCarList.contains(uc)) {
					service.modifyUserCar(uc);
				}
			}
			List<UserCarKey> oldCarKeyList = userCarList.stream().map(uc -> uc.getId())
								.collect(Collectors.toList());
			List<UserCarKey> newCarKeyList = updatedUser.getUserCar().stream().map(uc -> uc.getId())
					.collect(Collectors.toList());
			
			for (UserCarKey k : oldCarKeyList) {
				if (!newCarKeyList.contains(k)) {
					service.deleteUserCar(k);
				}
			}
			updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
			return ResponseEntity.status(202).body(repo.save(updatedUser));
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			return ResponseEntity.status(404).body("Car with id = " + id + " not found");
		}
		else {
			if (found.isPresent()) {
				Collection<UserCar> userCarList = found.get().getUserCar();
				for (UserCar e: userCarList) {
					service.deleteUserCar(e.getId());
				}
			}
			repo.deleteById(id);
			return ResponseEntity.status(202).body("Car deleted");
		}
	}

}

























