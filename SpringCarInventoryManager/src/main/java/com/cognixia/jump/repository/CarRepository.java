package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	public List<Car> findAll();
	
	@Query(value = "SELECT * FROM Car WHERE visibility = 1",
			nativeQuery = true)
	public List<Car> findAllAvailableCar();
	
	public Optional<Car> findById(int id);
	
	public List<Car> findByModel(String model);
	
	public List<Car> findByMake(String make);
	
	public List<Car> findByYear(int year);
	
	public List<Car> findByColor(String color);
	
}
