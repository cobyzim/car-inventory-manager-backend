package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/cars/add")
	public ResponseEntity<?> addCar(@RequestBody Car newCar){
		// TODO: Implement adding car
		return ResponseEntity.status(201).body("ok");
	}
	
	
}
