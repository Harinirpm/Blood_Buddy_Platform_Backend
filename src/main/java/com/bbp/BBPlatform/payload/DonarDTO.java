package com.bbp.BBPlatform.payload;


public class DonarDTO {
	String name;
	String password;
	String email;
	String role;
	public DonarDTO(String name, String password, String email, String role) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
}
