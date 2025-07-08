package com.bbp.BBPlatform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bbp.BBPlatform.model.UserEntity;
import com.bbp.BBPlatform.security.JwtUtilBBP;
import com.bbp.BBPlatform.service.UserEntityService;


@RestController
public class UserController {
	@Autowired	
	UserEntityService userService;
	@Autowired
	JwtUtilBBP jwtutil;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/addUser")
	public UserEntity addUser(@RequestBody UserEntity user) {
		return userService.addUser(user);
	}
	
	//register as new user
	@PostMapping("/register")
	public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity user){
		UserEntity savedUser = userService.addUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}

	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/getAllUsers")
	public List<UserEntity> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/users/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Welcome , this is your profile page.");
    }
	
	@GetMapping("/admin/test-auth")
	public ResponseEntity<?> testAuth(Authentication authentication) {
	    return ResponseEntity.ok(authentication.getAuthorities());
	}

}
