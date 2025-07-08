package com.bbp.BBPlatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	private String name;
	private String password;
	@Column(unique = true, nullable = false)
	private String email;
	private String role;
	
	public UserEntity() {
		
	}
	public UserEntity(String name,String password,String email,String role) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
}
