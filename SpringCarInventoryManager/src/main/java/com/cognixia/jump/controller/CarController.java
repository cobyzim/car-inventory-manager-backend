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

import com.cognixia.jump.model.Car;
import com.cognixia.jump.repository.CarRepository;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class CarController {

	@Autowired
	CarRepository repo;
	
	@CrossOrigin
	@GetMapping("/cars")
	public List<Car> getAllCars(){
		return repo.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/cars/available")
	public List<Car> getAllAvailableCars() {
		return repo.findAllAvailableCar();
	}
	
	@CrossOrigin
	@GetMapping("/cars/{id}")
	public ResponseEntity<?> getCar(@PathVariable int id){
		Optional<Car> carOpt = repo.findById(id);
		
		if(carOpt.isPresent()) {
			return ResponseEntity.status(200).body(carOpt.get());
		} else {
			return ResponseEntity.status(404).body("Car with id = " + id + " not found");
		}
	}
	
		@CrossOrigin
	@PostMapping("/cars/add")
	public ResponseEntity<?> addCar(@RequestBody Car newCar){
		newCar.setId(-1);
		return ResponseEntity.status(201).body(repo.save(newCar));
	}
	
	@CrossOrigin
	@PutMapping("/cars/update")
	public ResponseEntity<?> updateCar(@RequestBody Car updatedCar){
		Optional<Car> found = repo.findById(updatedCar.getId());
		
		if (found.isPresent()) {
			return ResponseEntity.status(202).body(repo.save(updatedCar));
		} else {
			return ResponseEntity.status(404).body("Car with id = " + updatedCar.getId() + " not found.");
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/cars/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable int id){
		Optional<Car> found = repo.findById(id);
		
		if (found.isPresent()) {
			repo.deleteById(id);
			return ResponseEntity.status(202).body("Car deleted.");
		} else {
			return ResponseEntity.status(404).body("Car with id = " + id + " not found." );
		}
	}
	
	
}
