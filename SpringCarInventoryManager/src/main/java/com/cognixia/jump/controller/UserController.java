package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
	
	//@Autowired
	//UserRepository repo;
	
	//@Autowired
	//PasswordEncoder encoder;
	
	@CrossOrigin
	@GetMapping("/user")
	public List<User> getAllUsers() {
		//return repo.findAll();
	}
}
