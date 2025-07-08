package com.bbp.BBPlatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.model.LoginRequest;
import com.bbp.BBPlatform.model.UserEntity;
import com.bbp.BBPlatform.repository.UserEntityRepository;

@Service
public class AuthenticationService {
	
	    private final UserEntityRepository userRepo;
	    private final AuthenticationManager authenticationManager;
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	    @Autowired
	    public AuthenticationService(UserEntityRepository userRepo, AuthenticationManager authenticationManager) {
	        this.userRepo = userRepo;
	        this.authenticationManager = authenticationManager;
	    }

	    public UserEntity loginAuthenticate(LoginRequest loginRequest) {
	        // Authenticate the user using email and password
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                )
	        );
	  
	        UserEntity user = userRepo.findByEmail(loginRequest.getEmail())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        
	        System.out.println("Email from request: " + loginRequest.getEmail());
	        System.out.println("Raw password from request: " + loginRequest.getPassword());
	        System.out.println("Encoded password in DB: " + user.getPassword());
	        System.out.println("Password match result: " + passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));

	        return user;
	    }
}
