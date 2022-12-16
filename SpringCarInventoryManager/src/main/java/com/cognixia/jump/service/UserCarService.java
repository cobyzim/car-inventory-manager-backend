package com.cognixia.jump.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Car;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.UserCar;
import com.cognixia.jump.model.UserCarKey;
import com.cognixia.jump.repository.CarRepository;
import com.cognixia.jump.repository.UserCarRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserCarService {

	@Autowired
	UserCarRepository repo;
	
	@Autowired
	UserRepository uRepo;
	
	@Autowired
	CarRepository cRepo;
	
	
	public UserCar modifyUserCar(UserCar userCar) {
		return repo.save(userCar);
	}
	
	public UserCar deleteUserCar(UserCarKey userCarKey) {
		UserCar toDelete = repo.findById(userCarKey).get();
		User updatedUser = uRepo.findById(toDelete.getUser().getId()).get();
		Collection<UserCar> tempList;
		tempList = updatedUser.getUserCar();
		tempList.remove(toDelete);
		updatedUser.setUserCar(tempList);
		Car updatedCar = cRepo.findById(toDelete.getCar().getId()).get();
		tempList = updatedCar.getUserCar();
		tempList.remove(toDelete);
		updatedCar.setUserCar(tempList);
		uRepo.save(updatedUser);
		cRepo.save(updatedCar);
		repo.delete(toDelete);
		return toDelete;
	}
}
