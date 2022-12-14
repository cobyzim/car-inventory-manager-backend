package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(nullable = false)  // make can't be null
	private String make;
	
	@NotBlank
	@Column(nullable = false)  // model can't be null
	private String model;
	
	//@NotBlank
	@Column(nullable = false, columnDefinition = "int default 0")
	private Integer year;
	
	@NotBlank
	@Column(nullable = false)
	private String color;
	
	@Column(columnDefinition = "boolean default true")
	private boolean visibility;
	
	@OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
	private Collection<UserCar> userCar = new ArrayList<>();
	
	public Car() {
		
	}

	public Car(Integer id, @NotBlank String make, @NotBlank String model, /*@NotBlank */ Integer year,
			@NotBlank String color, boolean visibility) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.year = year;
		this.color = color;
		this.visibility = visibility;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public Collection<UserCar> getUserCar() {
		return userCar;
	}

	public void setUserCar(Collection<UserCar> userCar) {
		this.userCar = userCar;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", model=" + model + ", year=" + year + ", color=" + color
				+ ", visibility=" + visibility + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, id, make, model, userCar, visibility, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(color, other.color) && Objects.equals(id, other.id) && Objects.equals(make, other.make)
				&& Objects.equals(model, other.model) && Objects.equals(userCar, other.userCar)
				&& visibility == other.visibility && Objects.equals(year, other.year);
	}
	
	
}
