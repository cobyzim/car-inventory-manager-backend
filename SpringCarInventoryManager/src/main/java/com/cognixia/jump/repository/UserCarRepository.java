package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognixia.jump.model.UserCar;
import com.cognixia.jump.model.UserCarKey;

public interface UserCarRepository extends JpaRepository<UserCar, UserCarKey> {

	@Query(value = "SELECT user_id FROM User_Cars WHERE car_id = ?",
			nativeQuery = true)
	public List<UserCar> findUsersByCarId(int id);
	
	@Query(value = "SELECT car_id FROM User_Cars WHERE user_id = ?",
			nativeQuery = true)
	public List<UserCar> findCarsByUserId(int id);
}
