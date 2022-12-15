package com.cognixia.jump.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.model.Car;
import com.cognixia.jump.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CarController.class)
public class CarControllerTest {

	private static final String STARTING_URI = "http://localhost:8080/api";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CarRepository repo;

	@InjectMocks
	private CarController controller;

	@Test
	@WithMockUser(roles = {})
	void testGetAllCars() throws Exception {
		String uri = STARTING_URI + "/cars";

		List<Car> allCars = new ArrayList<Car>();
		allCars.add(new Car(1, "Toyota", "Camry", 2002, "Gray", true));
		allCars.add(new Car(2, "Toyota", "Highlander", 2013, "Red", true));
		allCars.add(new Car(3, "Honda", "Civic", 2020, "Black", false));

		when(repo.findAll()).thenReturn(allCars);

		mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.length()").value(allCars.size()))
				.andExpect(jsonPath("$[0].id").value(allCars.get(0).getId()))
				.andExpect(jsonPath("$[1].make").value(allCars.get(1).getMake()))
				.andExpect(jsonPath("$[2].model").value(allCars.get(2).getModel()));
	}

	@Test
	@WithMockUser
	void testGetAvailableCars() throws Exception {
		String uri = STARTING_URI + "/cars/available";

		List<Car> allCars = new ArrayList<Car>();
		allCars.add(new Car(1, "Toyota", "Camry", 2002, "Gray", true));
		allCars.add(new Car(2, "Toyota", "Highlander", 2013, "Red", true));

		when(repo.findAllAvailableCar()).thenReturn(allCars);

		mvc.perform(get(uri)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.length()").value(allCars.size()))
				.andExpect(jsonPath("$[0].id").value(allCars.get(0).getId()))
				.andExpect(jsonPath("$[1].make").value(allCars.get(1).getMake()));

	}
	
	@Test
	@WithMockUser
	void testGetCar() throws Exception {
		String uri = STARTING_URI + "/cars/{id}";
		int id = 1;
		
		Car car = new Car(id, "Toyota", "Camry", 2002, "Gray", true);
		
		when (repo.findById(id)).thenReturn(Optional.of(car));
		
		mvc.perform(get(uri, id))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(car.getId()))
			.andExpect(jsonPath("$.make").value(car.getMake()))
			.andExpect(jsonPath("$.model").value(car.getModel()))
			.andExpect(jsonPath("$.year").value(car.getYear()))
			.andExpect(jsonPath("$.color").value(car.getColor()))
			.andExpect(jsonPath("$.visibility").value(car.isVisibility()));
		
	}
	
	@Test
	@WithMockUser
	void testGetCarNotFound() throws Exception {
		String uri = STARTING_URI + "/cars/{id}";
		int id = 1;
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(get(uri, id))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testGetAllCarsUnauthorized() throws Exception {
		String uri = STARTING_URI + "/cars";
	
		when(repo.findAll()).thenThrow(new AuthorizationServiceException(""));
		
		mvc.perform(get(uri))
				.andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testCreateCar() throws Exception {
		String uri = STARTING_URI + "/cars/add";
		
		Car car = new Car(1, "Toyota", "Camry", 2002, "Gray", true);
		
		when(repo.save(car)).thenReturn(car);
		
		mvc.perform(post(uri).content(asJsonString(car))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testUpdateCar() throws Exception {
		String uri = STARTING_URI + "/cars/update";
		int id = 1;
		Car car = new Car(id, "Toyota", "Camry", 2002, "Gray", true);
		
		when(repo.findById(id)).thenReturn(Optional.of(car));
		when(repo.save(car)).thenReturn(car);
		
		mvc.perform(put(uri).content(asJsonString(car)))
			.andExpect(status().isAccepted());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testUpdateCarNotFound() throws Exception {
		String uri = STARTING_URI + "/cars/update";
		int id = 1;
		Car car = new Car(id, "Toyota", "Camry", 2002, "Gray", true);
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(put(uri).content(asJsonString(car)))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testDeleteCar() throws Exception {
		String uri = STARTING_URI + "/cars/delete/{id}";
		int id = 1;
		Car car = new Car(id, "Toyota", "Camry", 2002, "Gray", true);
		
		when(repo.findById(id)).thenReturn(Optional.of(car));
		
		mvc.perform(delete(uri, id))
		.andExpect(status().isAccepted());
	}
	
	@Test
	@WithMockUser(roles = {})
	void testDeleteCarNotFound() throws Exception {
		String uri = STARTING_URI + "/cars/delete/{id}";
		int id = 1;
		
		when(repo.findById(id)).thenReturn(Optional.empty());
		
		mvc.perform(delete(uri, id))
			.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(final Object obj) {

		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}

	}
}
