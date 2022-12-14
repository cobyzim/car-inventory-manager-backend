package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// Class that will hold different parts of the composite key (user_id and car_id)
@Embeddable  // declares that class will be embedded by another entity (the UserCar class)
public class UserCarKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "car_id")
	private Integer carId;
	
	public UserCarKey() {
		
	}

	public UserCarKey(Integer userId, Integer carId) {
		super();
		this.userId = userId;
		this.carId = carId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCarKey other = (UserCarKey) obj;
		return Objects.equals(carId, other.carId) && Objects.equals(userId, other.userId);
	}
	
	

}
