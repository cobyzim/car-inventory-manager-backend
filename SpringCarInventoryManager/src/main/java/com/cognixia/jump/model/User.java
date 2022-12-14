package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static enum Role {
		ROLE_CUSTOMER, ROLE_ADMIN, ROLE_EMPLOYEE
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(unique = true, nullable = false)  // want username to be unique and not null
	private String username;
	
	@NotBlank
	@Column(nullable = false)  // password can't be null
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)  // role can't be null
	private Role role;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled;
	
	@Column(nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<UserCar> userCar = new ArrayList<>();
	
	public User() {
		
	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, Role role, boolean enabled,
			String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<UserCar> getUserCar() {
		return userCar;
	}

	public void setUserCar(Collection<UserCar> userCar) {
		this.userCar = userCar;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", enabled="
				+ enabled + ", email=" + email + "]";
	}
	
	
}
