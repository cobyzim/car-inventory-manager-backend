package com.cognixia.jump.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

// Entity class to model the join table
// Without this, won't be able to add the is_test_drive property
@Entity
public class UserCar {

	@EmbeddedId
	private UserCarKey id;
	
	@ManyToOne
	@MapsId("userId")  // MapsId -> ties field to part of composite key. Can't have actual entity in composite key
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@MapsId("carId")
	@JoinColumn(name = "car_id")
	private Car car;
	
	private boolean isTestDrive;
	
	public UserCar() {
		
	}

	public UserCar(UserCarKey id, User user, Car car, boolean isTestDrive) {
		super();
		this.id = id;
		this.user = user;
		this.car = car;
		this.isTestDrive = isTestDrive;
	}

	public UserCarKey getId() {
		return id;
	}

	public void setId(UserCarKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public boolean isTestDrive() {
		return isTestDrive;
	}

	public void setTestDrive(boolean isTestDrive) {
		this.isTestDrive = isTestDrive;
	}
	
}
