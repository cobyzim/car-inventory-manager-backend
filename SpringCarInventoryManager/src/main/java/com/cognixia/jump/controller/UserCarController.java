package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.UserCar;
import com.cognixia.jump.model.UserCarKey;
import com.cognixia.jump.repository.UserCarRepository;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class UserCarController {

	@Autowired
	UserCarRepository repo;
	
	@CrossOrigin
	@GetMapping("/link")
	public List<UserCar> getAllUserCars() {
		return repo.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/link?user={id}")
	public List<UserCar> getAllByUserId(@PathVariable int id) {
		return repo.findCarsByUserId(id);
	}
	
	@CrossOrigin
	@GetMapping("/link?car={id}")
	public List<UserCar> getAllByCarId(@PathVariable int id) {
		return repo.findUsersByCarId(id);
	}
	
	@CrossOrigin
	@PostMapping("/link/add")
	public ResponseEntity<?> addUserCar(@RequestBody UserCar newUserCar) {
		return ResponseEntity.status(201).body(repo.save(newUserCar));
	}
	
	@CrossOrigin
	@PutMapping("/link/update")
	public ResponseEntity<?> updateUserCar(@RequestBody UserCar updatedUserCar) {
		Optional<UserCar> found = repo.findById(updatedUserCar.getId());
		
		if (found.isPresent()) {
			return ResponseEntity.status(202).body(repo.save(updatedUserCar));
		} else {
			return ResponseEntity.status(404).body("UserCar not found");
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/link/delete")
	public ResponseEntity<?> deleteUserCar(@RequestBody UserCarKey id) {
		Optional<UserCar> found = repo.findById(id);
		
		if (found.isPresent()) {
			repo.delete(found.get());
			return ResponseEntity.status(202).body("Link deleted");
		} else {
			return ResponseEntity.status(404).body("UserCar not found");
		}
	}
}
