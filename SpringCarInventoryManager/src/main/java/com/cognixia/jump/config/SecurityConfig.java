package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@Configuration  //indicates class has @Bean definition methods
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;  // Spring will look for an implementation of userDetailsService and find ours
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Bean
	protected UserDetailsService userDetailsService() { 
		
		return userDetailsService;
	}
	
	// Handles authorization
	// SecurityFilterChain makes sure that we go through the filters first before we get to anything else
	//      It needs to make sure that JwtRequestFilter gets checked first though
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
				.authorizeHttpRequests()
				.antMatchers("/authenticate").permitAll()
				.antMatchers(HttpMethod.POST, "/api/user").permitAll()
				.antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")  //GOOD
				.antMatchers("/api/user/{id}").hasRole("ADMIN") //GOOD FOR GET AND POST
				.antMatchers(HttpMethod.PUT, "/api/user").hasRole("ADMIN") //GOOD
				.antMatchers("/api/cars").hasRole("ADMIN")
				.antMatchers("/api/cars/add").hasRole("ADMIN")
				.antMatchers("/api/cars/update").hasRole("ADMIN")
				.antMatchers("/api/cars/delete/{id}").hasRole("ADMIN")
				.antMatchers("/api/cars/available").hasAnyRole("ADMIN", "CUSTOMER")
				.antMatchers("/api/cars/{id}").hasAnyRole("ADMIN", "CUSTOMER")
				.anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// Typically the first filter that is checked is the UsernamePasswordAuthenticationFilter, however if this filter is
		// checked first, there is no username or password to check, so security will block the request
		// so we make sure the jwt filter is checked first, so we can load the jwt in and load the user before any other filter
		// is checked
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	// encode/decode all user passwords
	@Bean
	protected PasswordEncoder encoder() {
		
		// use BCrypt library to encode password
		return new BCryptPasswordEncoder();
	}
	
	// load the encoder & user details service that are needed for spring security to do authentication
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		
		return authProvider;
	}
	
	// can autowire and access the authentication manager (manages authentication (login) of our project)
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	
}
