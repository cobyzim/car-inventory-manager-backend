package com.cognixia.jump.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Car;
import com.cognixia.jump.model.UserCar;
import com.cognixia.jump.model.UserCarKey;
import com.cognixia.jump.repository.CarRepository;
import com.cognixia.jump.service.UserCarService;

@CrossOrigin
@RequestMapping("/api")
@RestController
public class CarController {

	@Autowired
	CarRepository repo;
	
	@Autowired
	UserCarService service;
	
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
	public ResponseEntity<?> getCar(@PathVariable int id) throws ResourceNotFoundException {
		Optional<Car> carOpt = repo.findById(id);
		
		if(carOpt.isPresent()) {
			return ResponseEntity.status(200).body(carOpt.get());
		} else {
			throw new ResourceNotFoundException("Car with id = " + id + " not found.");
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
	public ResponseEntity<?> updateCar(@RequestBody Car updatedCar) throws ResourceNotFoundException {
		Optional<Car> found = repo.findById(updatedCar.getId());
		
		if (found.isPresent()) {
			Collection<UserCar> userCarList = found.get().getUserCar();
			for (UserCar uc: updatedCar.getUserCar()) {
				if (!userCarList.contains(uc)) {
					service.modifyUserCar(uc);
				}
			}
			List<UserCarKey> oldCarKeyList = userCarList.stream().map(uc -> uc.getId())
								.collect(Collectors.toList());
			List<UserCarKey> newCarKeyList = updatedCar.getUserCar().stream().map(uc -> uc.getId())
					.collect(Collectors.toList());
			
			for (UserCarKey k : oldCarKeyList) {
				if (!newCarKeyList.contains(k)) {
					service.deleteUserCar(k);
				}
			}
			
			return ResponseEntity.status(202).body(repo.save(updatedCar));
		} else {
			throw new ResourceNotFoundException("Car with id = " + updatedCar.getId() + " not found and couldn't be updated.");
		}
	}
	
	@CrossOrigin
	@DeleteMapping("/cars/delete/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable int id) throws ResourceNotFoundException {
		Optional<Car> found = repo.findById(id);
		
		if (found.isPresent()) {
			Collection<UserCar> userCarList = found.get().getUserCar();
			for (UserCar e: userCarList) {
				service.deleteUserCar(e.getId());
			}
			repo.deleteById(id);
			return ResponseEntity.status(202).body("Car deleted.");
		} else {
			throw new ResourceNotFoundException("Car with id = " + id + " not foun and couldn't be deleted.");
		}
	}
	
	
}
