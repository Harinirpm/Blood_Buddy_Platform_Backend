package com.bbp.BBPlatform.payload;

public class LoginResponse {
	 private String email;
	    private String role;
	    private String name;

	    public LoginResponse(String email, String role, String name) {
	        this.email = email;
	        this.role = role;
	        this.name = name;
	    }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getRole() { return role; }
	    public void setRole(String role) { this.role = role; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

}
